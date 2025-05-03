package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.repository.BookingRepository
import org.springframework.stereotype.Service

@Service
class BookingService(
    private val bookingRepository: BookingRepository
) {
    // TODO: Validate (showtime and seats before adding)
    fun add(booking: Booking) = bookingRepository.save(booking)
    fun getAll(): List<Booking> = bookingRepository.findAll()

    fun updateById(id: Long, booking: Booking) {
        val b = bookingRepository.findById(id).orElseThrow { ResourceNotFoundException("f") }
        bookingRepository.save(
            b.copy(
//                TODO: update booking
            )
        )
    }
}