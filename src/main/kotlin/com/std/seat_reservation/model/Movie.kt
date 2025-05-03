package com.std.seat_reservation.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.Date

@Entity
data class Movie(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val title: String,
    val description: String,
    val genre: String,
    val duration: String,
    val rating: Float,
    val releaseDate: Date,
)