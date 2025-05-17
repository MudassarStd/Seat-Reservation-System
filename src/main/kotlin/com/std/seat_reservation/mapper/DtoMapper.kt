package com.std.seat_reservation.mapper

import com.std.seat_reservation.dto.*
import com.std.seat_reservation.model.*

fun AuthRequest.toUser(hashedPassword: String) = User(
    email = this.email,
    password = hashedPassword
)

fun BookingRequest.toBooking(user: User, showtime: Showtime) = Booking(
    user = user,
    showtime = showtime,
    seats = this.seats
)

fun ShowtimeRequest.toShowtime(movie: Movie, theater: String) = Showtime(
    movie = movie,
    theater = theater,
    availableSeats = this.availableSeats,
    date = this.date,
    startTime = this.startTime,
    endTime = this.endTime
)

fun Showtime.toResponse(): ShowtimeResponse {
    return ShowtimeResponse(
        id = this.id,
        movieId = this.movie.id,
        theater = this.theater,
        availableSeats = this.availableSeats,
        date = this.date,
        startTime = this.startTime,
        endTime = this.endTime
    )
}

fun Booking.toBookingResponse() = BookingResponse(
    seats = this.seats,
    status = this.status,
    showtime = this.showtime.toResponse()
)

fun ReviewRequest.toReview(user: User, movie: Movie) = Review(
    user = user,
    movie = movie,
    rating = this.rating,
    comment = this.comment
)

fun Review.toResponse() = ReviewResponse(
    id = id,
    userId = user.id,
    rating = rating,
    comment = comment,
    createdAt = createdAt
)