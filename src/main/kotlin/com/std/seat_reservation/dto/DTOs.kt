package com.std.seat_reservation.dto

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)