package com.std.seat_reservation.controller

import com.std.seat_reservation.model.Theater
import com.std.seat_reservation.service.TheaterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/theaters")
class TheaterController(
    private val theaterService: TheaterService
) {
    @PostMapping
    fun add(@RequestBody theater: Theater) = theaterService.add(theater)

    @GetMapping
    fun getAll() = theaterService.getAll()
}