package com.std.seat_reservation.dto

import java.time.LocalDateTime

data class ReviewRequest(
    val rating: Int,
    val comment: String,
)

data class ReviewResponse(
    val id: Long,
    val rating: Int,
    val comment: String,
    val dateTime: LocalDateTime
)