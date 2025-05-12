package com.std.seat_reservation.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Showtime(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne(cascade = [CascadeType.ALL])
    val movie: Movie,
//    @OneToOne
//    val theater: Theater,
    val theater: String,
    val availableSeats: Int,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)