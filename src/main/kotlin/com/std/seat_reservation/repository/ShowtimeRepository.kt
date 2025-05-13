package com.std.seat_reservation.repository

import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.model.Showtime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShowtimeRepository: JpaRepository<Showtime, Long> {
    fun existsByMovie(movie: Movie): Boolean
}