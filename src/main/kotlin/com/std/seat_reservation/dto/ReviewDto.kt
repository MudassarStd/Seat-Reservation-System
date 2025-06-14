package com.std.seat_reservation.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

data class ReviewRequest(
    @field:Min(1)
    @field:Max(5)
    val rating: Int,
    val comment: String,
)

data class ReviewResponse(
    val id: Long,
    val userId: Long,
    val rating: Int,
    val comment: String,
    val createdAt: LocalDateTime?
)