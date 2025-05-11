package com.std.seat_reservation.repository

import com.std.seat_reservation.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BookingRepository: JpaRepository<Booking, Long> {
    fun findAllByUserId(userId: Long): List<Booking>?
    fun findByUserId(userId: Long): Optional<Booking>
    fun deleteByUserId(userId: Long)
}