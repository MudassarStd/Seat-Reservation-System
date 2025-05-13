package com.std.seat_reservation.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime

data class BookingRequest(
    val seats: Int,
    val showtimeId: Long
)

data class ShowtimeRequest (
    @field:NotNull
    val movieId: Long,
    val theaterName: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    @field:Min(50)
    @field:Max(500)
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
