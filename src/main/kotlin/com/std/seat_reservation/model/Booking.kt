package com.std.seat_reservation.model

import jakarta.persistence.*

@Entity
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
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