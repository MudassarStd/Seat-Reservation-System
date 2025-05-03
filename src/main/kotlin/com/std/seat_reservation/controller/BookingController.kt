package com.std.seat_reservation.controller

import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.service.BookingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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


}