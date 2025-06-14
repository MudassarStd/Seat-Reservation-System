package com.std.seat_reservation.dto

import com.std.seat_reservation.model.User

data class AuthRequest(
    val name: String? = null,
    val email: String,
    val password: String
)

data class ProfileUpdateRequest(
    val name: String,
    val email: String
)

data class PasswordResetRequest(
    val currentPassword: String,
    val email: String,
    val newPassword: String,
)

// consider adding token expire time for client to discard it on expiry
data class AuthResponse(
    val token: String,
//    val expiresAt: Long,
    val user: UserResponse
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String
)

fun User.toAuthResponse() = UserResponse(
    id = this.id,
    name = this.name,
    email = this.email,
    role = this.role.toString()
)