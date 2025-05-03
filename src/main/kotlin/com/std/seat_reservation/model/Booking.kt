package com.std.seat_reservation.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Booking(
    @Id
    val id: Long,
    @OneToOne
    val user: User,
    @OneToOne
    val showtime: Showtime,
    val seats: Int,
    val status: BookingStatus
)

enum class BookingStatus {
    Booked, Cancelled
}