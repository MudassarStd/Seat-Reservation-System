package com.std.seat_reservation.mapper

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.model.User

fun AuthRequest.toUser(hashedPassword: String) = User(
    email = this.email,
    password = hashedPassword
)