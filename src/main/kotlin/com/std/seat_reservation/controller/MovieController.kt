package com.std.seat_reservation.controller

import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.service.MovieService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService
) {
    @PostMapping
    fun add(@RequestBody movie: Movie) = movieService.add(movie)

    @GetMapping
    fun getAll(
        @RequestParam(required = false) genre: String?,
        @RequestParam(required = false) rating: Float?,
    ) = movieService.getAll(genre, rating)

    @GetMapping("/id")
    fun getById(@PathVariable id: Long) = movieService.getById(id)


}