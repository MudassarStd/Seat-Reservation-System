package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.ReviewRequest
import com.std.seat_reservation.service.ReviewService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("/me")
    fun getMyReviews() = reviewService.getMyReviews()

    @DeleteMapping("/{id}")
    fun deleteMyReview(@PathVariable id: Long) = reviewService.deleteReview(id)

    @PutMapping("/{id}")
    fun updateReview(@PathVariable id: Long, @RequestBody request: ReviewRequest) =
        reviewService.updateReview(id, request)

    // ************************ ADMIN ONLY ************************
    @GetMapping("/all")
    fun getAll() = reviewService.getAll()
}