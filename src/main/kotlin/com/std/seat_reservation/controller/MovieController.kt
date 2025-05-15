package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.ReviewRequest
import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.model.Review
import com.std.seat_reservation.service.MovieService
import com.std.seat_reservation.service.ReviewService
import com.std.seat_reservation.service.ShowtimeService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService,
    private val showtimeService: ShowtimeService,
    private val reviewService: ReviewService
) {
    @PostMapping
    fun add(@RequestBody movie: Movie) = movieService.add(movie)

    @PostMapping("/list")
    fun addMany(@RequestBody movies: List<Movie>) = movieService.addBatch(movies)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = movieService.deleteById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody movie: Movie) = movieService.updateById(id, movie)

    @GetMapping
    fun getAll(
        @RequestParam(required = false) genre: String?,
        @RequestParam(required = false) rating: Float?,
        @RequestParam(required = false) releaseDate: LocalDate?
    ) = movieService.getAll(genre, rating, releaseDate)

    @GetMapping("/{id}/showtimes")
    fun getShowtimesByMovie(@PathVariable id: Long) = showtimeService.getByMovie(id)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = movieService.getById(id)

    @PostMapping("/{id}/reviews")
    fun addReview(@PathVariable id: Long, @RequestBody review: ReviewRequest) = reviewService.addReview(id, review)

    @GetMapping("/{id}/reviews")
    fun getReviews(@PathVariable id: Long) = reviewService.getAllByMovie(id)

//    @GetMapping("/recommendations")
//    fun getRecommendations() = movieService.getRecommendations()
}