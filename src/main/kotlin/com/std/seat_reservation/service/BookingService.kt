package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.model.BookingStatus
import com.std.seat_reservation.repository.BookingRepository
import com.std.seat_reservation.util.SecurityUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.math.atan

// Bookings Operations:

@Service
class BookingService(
    private val bookingRepository: BookingRepository
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
    fun updateMyBooking(booking: Booking) {
        bookingRepository.save(
            getByUserId(SecurityUtil.getCurrentAuthenticatedUser().id).copy(
                seats = booking.seats,
                status = booking.status
            )
        )
        TODO(reason = "Also update seat count in showtime on update of seats or status")
    }

    // later add filters such as movies, show times or theaters
    fun getMyBookings() = getBookingsByUserId(SecurityUtil.getCurrentAuthenticatedUser().id)

    fun deleteMyBooking() = bookingRepository.deleteByUserId(SecurityUtil.getCurrentAuthenticatedUser().id)

    @Transactional
    fun cancelMyBooking(bookingId: Long) {
        val booking = getById(bookingId).let {
            if (it.user == SecurityUtil.getCurrentAuthenticatedUser()) it else null
        }
        booking?.let {
            bookingRepository.save(
                    it.copy(
                        status = BookingStatus.Cancelled
                    )
            )
        }

        TODO(reason = "Update show time seats count")
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

    fun getBookingsByUserId(userId: Long) = bookingRepository.findAllByUserId(userId) ?: throw ResourceNotFoundException("Not found")

}