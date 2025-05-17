package com.std.seat_reservation.service

import com.std.seat_reservation.dto.ReviewRequest
import com.std.seat_reservation.dto.ReviewResponse
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toResponse
import com.std.seat_reservation.mapper.toReview
import com.std.seat_reservation.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import java.time.LocalDateTime

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val movieService: MovieService,
    private val authService: AuthService
) {
    private fun getReview(id: Long) =
        reviewRepository.findById(id).orElseThrow { ResourceNotFoundException("Review not found") }

    // if a movie exists in user reviews then don't allow
    fun addReview(movieId: Long, request: ReviewRequest): String {
        val movie = movieService.getById(movieId)
        val user = authService.getCurrentAuthenticatedUser()
        if (reviewRepository.existsByUserAndMovie(user, movie)) throw IllegalStateException("User has already a review added for this movie")
        reviewRepository.save(request.toReview(user, movie))
        return "Review added successfully"
    }

    fun getAllByMovie(movieId: Long): List<ReviewResponse> = reviewRepository.findByMovieId(movieId).map {
        it.toResponse()
    }

    // FOR ADMIN
//    fun getAllByUser(userId: Long): List<ReviewResponse> = reviewRepository.findByUserId(userId)?.map {
//        it.toResponse()
//    } ?: throw ResourceNotFoundException("No reviews for this user")

    fun getMyReviews() = reviewRepository.findByUserId(authService.getCurrentAuthenticatedUser().id)

    fun deleteReview(id: Long): String {
        return if (getReview(id).user == authService.getCurrentAuthenticatedUser()) {
            reviewRepository.deleteById(id)
            "Deleted Review"
        } else {
            throw IllegalStateException("User is not allowed to delete this review")
        }
    }

    fun updateReview(id: Long, reviewRequest: ReviewRequest) {
        val review = getReview(id)
        if (review.user == authService.getCurrentAuthenticatedUser()) {
            reviewRepository.save(
                review.copy(
                    rating = reviewRequest.rating,
                    comment = reviewRequest.comment,
                    updatedAt = LocalDateTime.now()
                )
            )
        } else {
            throw IllegalStateException("User is not allowed to update this review")
        }
    }

    fun getAll() = reviewRepository.findAll()
}