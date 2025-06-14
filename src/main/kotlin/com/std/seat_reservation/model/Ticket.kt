package com.std.seat_reservation.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Ticket(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ticketNumber: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "booking_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val booking: Booking
)
