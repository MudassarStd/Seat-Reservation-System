package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.BookingRequest
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.service.BookingService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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
    fun add(
        @RequestBody booking: Booking
    ) = bookingService.add(booking)

    @PostMapping("/me")
    fun createMyBooking(@RequestBody request: BookingRequest) = bookingService.createMyBooking(request)

    @GetMapping("/me")
    fun getMyBookings() = bookingService.getMyBookings()

    @DeleteMapping("/me")
    fun cancelMyBooking(@RequestParam id: Long) = bookingService.cancelMyBooking(id)
}