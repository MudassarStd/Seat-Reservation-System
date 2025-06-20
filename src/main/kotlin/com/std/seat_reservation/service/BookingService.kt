package com.std.seat_reservation.service

import com.std.seat_reservation.dto.BookingRequest
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toBooking
import com.std.seat_reservation.mapper.toBookingResponse
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.model.BookingStatus
import com.std.seat_reservation.repository.BookingRepository
import com.std.seat_reservation.util.SecurityUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.math.atan

// Bookings Operations: AdminOnly && user-specific

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val authService: AuthService,
    private val showtimeService: ShowtimeService
) {
    // TODO: Validate (showtime and seats before adding)
    fun add(booking: Booking) = bookingRepository.save(booking)

    // Admin Only
    fun getAll(): List<Booking> = bookingRepository.findAll()

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
    }

    @Transactional
    fun updateMyBooking(request: BookingRequest) {
        bookingRepository.save(
            getByUserId(authService.getCurrentAuthenticatedUser().id).copy(
                seats = request.seats,
//                status = booking.status
            )
        )
        showtimeService.updateSeatsOnBookingById(request.showtimeId, request.seats)
    }

    // later add filters such as movies, show times or theaters
    fun getMyBookings(status: BookingStatus?) = getBookingsByUserId(authService.getCurrentAuthenticatedUser().id, status)

    @Transactional
    fun deleteMyBooking(id: Long) {
        cancelMyBooking(id) // validates that booking belongs to user + update seat count of showtime
        bookingRepository.deleteById(id)
    }

    @Transactional
    fun cancelMyBooking(bookingId: Long): String {
        val booking = getById(bookingId).let {
            if (it.user == authService.getCurrentAuthenticatedUser()) it else throw ResourceNotFoundException("This booking does not belong to you")
        }
        bookingRepository.save(
            booking.copy(
                status = BookingStatus.Cancelled
            )
        )
        if (booking.status != BookingStatus.Cancelled) showtimeService.updateSeatsOnCancellationById(booking.showtime.id, booking.seats)

        return "Booking cancelled"
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

    fun getBookingsByUserId(userId: Long, status: BookingStatus? = null) = bookingRepository.findAllByUserId(userId)?.filter{
        it.status == status || status == null
    }?.map {
        it.toBookingResponse()
    } ?: throw ResourceNotFoundException("Not found")
}