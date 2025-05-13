package com.std.seat_reservation.controller

import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.service.MovieService
import com.std.seat_reservation.service.ShowtimeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService,
    private val showtimeService: ShowtimeService
) {
    @PostMapping
    fun add(@RequestBody movie: Movie) = movieService.add(movie)

    @PostMapping("/list")
    fun addMany(@RequestBody movies: List<Movie>) = movieService.addMany(movies)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = movieService.deleteById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody movie: Movie) = movieService.updateById(id, movie)

    @GetMapping
    fun getAll(
        @RequestParam(required = false) genre: String?,
        @RequestParam(required = false) rating: Float?,
    ) = movieService.getAll(genre, rating)

    @GetMapping("/{id}/showtimes")
    fun getShowtimesByMovie(@PathVariable id: Long) = showtimeService.getByMovie(id)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = movieService.getById(id)
}