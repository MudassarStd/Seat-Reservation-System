package com.std.seat_reservation.model

import jakarta.persistence.*
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
data class Showtime(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne
    val movie: Movie,
    @OneToOne
    val theater: Theater,
    val availableSeats: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)