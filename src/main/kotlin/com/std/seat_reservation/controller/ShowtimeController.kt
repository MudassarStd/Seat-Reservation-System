package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.ShowtimeRequest
import com.std.seat_reservation.service.ShowtimeService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/showtimes")
class ShowtimeController(
    private val showtimeService: ShowtimeService
) {
    @PostMapping
    fun add(@Valid @RequestBody showtimeRequest: ShowtimeRequest) = showtimeService.add(showtimeRequest)

    @GetMapping
    fun getAll() = showtimeService.getAll()

    @DeleteMapping
    fun delete(@PathVariable id: Long) = showtimeService.deleteById(id)

    @PutMapping
    fun update(@PathVariable id: Long, @RequestBody showtimeRequest: ShowtimeRequest) =
        showtimeService.update(id, showtimeRequest)
}