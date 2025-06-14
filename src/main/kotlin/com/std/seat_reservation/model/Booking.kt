package com.std.seat_reservation.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,
    @ManyToOne @JoinColumn(name = "showtimeId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val showtime: Showtime,
    val seats: Int,
    @Enumerated
    var status: BookingStatus = BookingStatus.Booked,
    val ticketNumber: Int
)

enum class BookingStatus {
    Booked, Cancelled
}