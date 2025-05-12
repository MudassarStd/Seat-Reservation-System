package com.std.seat_reservation.dto

import com.std.seat_reservation.model.User

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val name: String,
    val email: String,
    val role: String
)