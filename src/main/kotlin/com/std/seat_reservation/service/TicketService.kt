package com.std.seat_reservation.service

import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.model.Showtime
import com.std.seat_reservation.model.Ticket
import com.std.seat_reservation.repository.TicketRepository
import org.springframework.stereotype.Service

@Service
class TicketService(
    private val ticketRepository: TicketRepository
) {
    fun createTickets(seats: Int, booking: Booking) {
        repeat(seats) {
            ticketRepository.save(Ticket(booking = booking))
        }
    }
}