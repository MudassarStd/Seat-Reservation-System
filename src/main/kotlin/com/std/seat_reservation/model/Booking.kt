package com.std.seat_reservation.model

import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
data class Booking(
    @Id
    val id: Long,
    @OneToOne @JoinColumn(name = "userId")
    val user: User,
    @OneToOne @JoinColumn(name = "showtimeId")
    val showtime: Showtime,
    val seats: Int,
    @Enumerated
    var status: BookingStatus = BookingStatus.Booked
)

enum class BookingStatus {
    Booked, Cancelled
}