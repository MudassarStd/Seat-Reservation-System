package com.std.seat_reservation.util

import kotlin.random.Random

const val JWT_SECRET_KEY = "our-super-secret-key-1234567890-of-all-time"
const val TOKEN_EXPIRATION = 86400000L // 24 hr

fun random8DigitId(): Int {
    return Random.nextInt(10_000_000, 100_000_000)
}

