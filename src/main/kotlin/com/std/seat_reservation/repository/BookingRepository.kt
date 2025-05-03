package com.std.seat_reservation.repository

import com.std.seat_reservation.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository: JpaRepository<Booking, Long>