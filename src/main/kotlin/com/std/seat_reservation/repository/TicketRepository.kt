package com.std.seat_reservation.repository

import com.std.seat_reservation.model.Ticket
import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository: JpaRepository<Ticket, Long>