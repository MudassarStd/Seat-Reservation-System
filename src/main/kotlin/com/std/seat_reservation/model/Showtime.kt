package com.std.seat_reservation.model

import jakarta.persistence.*
import java.sql.Time
import java.util.*

@Entity
data class Showtime(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    val movie: Movie,
    @OneToOne
    val theater: Theater,
    val availableSeats: Int,
    val date: Date,
    val startTime: Time,
    val endTime: Time
)