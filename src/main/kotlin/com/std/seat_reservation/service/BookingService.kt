package com.std.seat_reservation.service

import com.std.seat_reservation.dto.BookingRequest
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toBooking
import com.std.seat_reservation.mapper.toBookingResponse
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.model.BookingStatus
import com.std.seat_reservation.model.Role
import com.std.seat_reservation.repository.BookingRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

// Bookings Operations: AdminOnly && user-specific

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val authService: AuthService,
    private val showtimeService: ShowtimeService,
    private val ticketService: TicketService,
    private val notificationService: NotificationService
) {
    // TODO: Validate (showtime and seats before adding)
    fun add(booking: Booking) = bookingRepository.save(booking)

    // Admin Only
//    fun getAll(): List<Booking> = bookingRepository.findAll()

    fun getById(id: Long) =
        bookingRepository.findById(id).orElseThrow { ResourceNotFoundException("Booking Not found") }

    fun updateById(id: Long, booking: Booking) {
        bookingRepository.save(
            getById(id).copy(
                seats = booking.seats,
                status = booking.status
            )
        )
    }

    @Transactional
    fun createMyBooking(request: BookingRequest) {
        val user = authService.getCurrentAuthenticatedUser()
        val showtime = showtimeService.getById(request.showtimeId)
        bookingRepository.save(
            request.toBooking(
                user = user,
                showtime = showtime
            )
        )
        showtimeService.updateSeatsOnBookingById(request.showtimeId, request.seats)
        notificationService.add("You have successfully booked ${request.seats} seats for ${showtime.movie.title}", user)
//        ticketService.createTickets(seats = request.seats, )
    }

    @Transactional
    fun updateMyBooking(id: Long, seats: Int) {
        val booking = getById(id)
        if (booking.user != authService.getCurrentAuthenticatedUser()) throw IllegalStateException("Booking does not belong to you")
        bookingRepository.save(
            booking.copy(
                seats = seats
            )
        )
        showtimeService.updateSeatsOnBookingById(booking.showtime.id, seats)
    }

    // later add filters such as movies, show times or theaters
    fun getMyBookings(status: BookingStatus?) =
        getBookingsByUserId(authService.getCurrentAuthenticatedUser().id, status)

    @Transactional
    fun deleteMyBooking(id: Long) {
        cancelMyBooking(id) // validates that booking belongs to user + update seat count of showtime
        bookingRepository.deleteById(id)
    }

    @Transactional
    fun cancelMyBooking(bookingId: Long): String {
        val booking = getById(bookingId)
        if (booking.user != authService.getCurrentAuthenticatedUser() && authService.getCurrentAuthenticatedUser().role != Role.ADMIN) throw IllegalStateException("This booking does not belong to you")
        bookingRepository.save(
            booking.copy(
                status = BookingStatus.Cancelled
            )
        )
        if (booking.status != BookingStatus.Cancelled) showtimeService.updateSeatsOnCancellationById(
            booking.showtime.id,
            booking.seats
        )
        notificationService.add("Your booking has been cancelled for ${booking.showtime.movie.title}", booking.user)
        return "Booking cancelled"
    }

    @Transactional
    fun cancelMyAllBookings() {
        val updateBookings = bookingRepository.findAllByUserId(authService.getCurrentAuthenticatedUser().id)?.let { bookings ->
            bookings.map { booking ->
                booking.status = BookingStatus.Cancelled

                // Update show times
                showtimeService.updateSeatsOnBookingById(
                    booking.showtime.id, booking.seats
                )
                booking
            }
        } ?: throw ResourceNotFoundException("No Bookings for this user")
        bookingRepository.saveAll(updateBookings)

        TODO("update all showtime seat count")

    }

    fun cancelAllByUserId(userId: Long) {
        val updateBookings = bookingRepository.findAllByUserId(userId)?.let { bookings ->
            bookings.map { booking ->
                booking.status = BookingStatus.Cancelled
                booking
            }
        } ?: throw ResourceNotFoundException("No Bookings for this user")

        bookingRepository.saveAll(updateBookings)
    }

    fun getByUserId(id: Long) =
        bookingRepository.findByUserId(id).orElseThrow { ResourceNotFoundException("Not found") }

    fun getBookingsByUserId(userId: Long, status: BookingStatus? = null) =
        bookingRepository.findAllByUserId(userId)?.filter {
            it.status == status || status == null
        }?.map {
            it.toBookingResponse()
        } ?: throw ResourceNotFoundException("Not found")

    fun getAllBookings() = bookingRepository.findAll()?.map {
        it.toBookingResponse()
    }
}