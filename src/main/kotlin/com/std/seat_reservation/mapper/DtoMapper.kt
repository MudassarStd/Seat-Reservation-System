package com.std.seat_reservation.mapper

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.dto.BookingRequest
import com.std.seat_reservation.dto.ShowtimeRequest
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

fun ShowtimeRequest.toShowtime(movie: Movie, theater: Theater) = Showtime(
    movie = movie,
    theater = theater,
    availableSeats = this.availableSeats,
    date = this.date,
    startTime = this.startTime,
    endTime = this.endTime
)