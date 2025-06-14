package com.std.seat_reservation.mapper

import com.std.seat_reservation.dto.*
import com.std.seat_reservation.model.*
import com.std.seat_reservation.util.random8DigitId

fun AuthRequest.toUser(hashedPassword: String) = User(
    email = this.email,
    name = name!!,
    password = hashedPassword
)

fun BookingRequest.toBooking(user: User, showtime: Showtime) = Booking(
    user = user,
    showtime = showtime,
    seats = this.seats,
    ticketNumber = random8DigitId()
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
    id = this.id,
    seats = this.seats,
    status = this.status,
    ticketNumber = this.ticketNumber,
    showStartTime = this.showtime.startTime,
    showEndTime = this.showtime.endTime,
    showDate = this.showtime.date,
    userEmail = this.user.email,
    movieTitle = this.showtime.movie.title,
    theater = this.showtime.theater
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