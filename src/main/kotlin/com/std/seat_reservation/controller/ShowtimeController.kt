package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.ShowtimeRequest
import com.std.seat_reservation.service.ShowtimeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/showtimes")
class ShowtimeController(
    private val showtimeService: ShowtimeService
) {
    @PostMapping
    fun add(@RequestBody showtimeRequest: ShowtimeRequest) = showtimeService.add(showtimeRequest)
}