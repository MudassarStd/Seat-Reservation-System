package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.BookingRequest
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.model.BookingStatus
import com.std.seat_reservation.service.BookingService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookings")
class BookingController(
    private val bookingService: BookingService
) {
    @GetMapping
    fun getAll() = bookingService.getAll()

    @PostMapping
    fun add(@RequestBody booking: Booking) = bookingService.add(booking)

    @PostMapping("/me")
    fun createMyBooking(@Valid @RequestBody request: BookingRequest) = bookingService.createMyBooking(request)

    @GetMapping("/me")
    fun getMyBookings(@RequestParam status: BookingStatus? = null) = bookingService.getMyBookings(status)

    @DeleteMapping("/me/cancel/{id}")
    fun cancelMyBooking(@PathVariable id: Long) = bookingService.cancelMyBooking(id)

    // Test above, all working fine

    @DeleteMapping("/me/{id}")
    fun deleteMyBooking(@PathVariable id: Long) = bookingService.deleteMyBooking(id)

    @PutMapping("/me")
    fun updateMyBooking(@Valid @RequestBody request: BookingRequest) = bookingService.updateMyBooking(request)
}