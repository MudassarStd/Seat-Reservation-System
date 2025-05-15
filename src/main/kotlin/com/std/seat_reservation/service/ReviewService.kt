package com.std.seat_reservation.service

import com.std.seat_reservation.dto.ReviewRequest
import com.std.seat_reservation.dto.ReviewResponse
import com.std.seat_reservation.mapper.toResponse
import com.std.seat_reservation.mapper.toReview
import com.std.seat_reservation.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val movieService: MovieService,
    private val authService: AuthService
) {

    fun addReview(movieId: Long, request: ReviewRequest): String {
        val movie = movieService.getById(movieId)
        val user = authService.getCurrentAuthenticatedUser()
        reviewRepository.save(request.toReview(user, movie))
        return "Review added successfully"
    }

    fun getAllByMovie(movieId: Long): List<ReviewResponse> = reviewRepository.findByMovieId(movieId).map {
        it.toResponse()
    }
    fun getAllByUser(userId: Long): List<ReviewResponse> = reviewRepository.findByUserId(userId).map {
        it.toResponse()
    }

}