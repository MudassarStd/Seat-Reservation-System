package com.std.seat_reservation.dto

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

data class BookingRequest(
    val seats: Int,
    val showtimeId: Long
)

data class ShowtimeRequest (
    val movieId: Long,
    val theaterName: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val availableSeats: Int
)

data class ShowtimeResponse(
    val id: Long,
    val movieId: Long,
    val theater: String,
    val availableSeats: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)
