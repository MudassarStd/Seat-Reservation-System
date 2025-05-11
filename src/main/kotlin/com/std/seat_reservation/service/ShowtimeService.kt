package com.std.seat_reservation.service

import com.std.seat_reservation.dto.ShowtimeRequest
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toShowtime
import com.std.seat_reservation.model.Showtime
import com.std.seat_reservation.repository.ShowtimeRepository
import org.springframework.stereotype.Service

@Service
class ShowtimeService(
    private val showtimeRepository: ShowtimeRepository,
    private val movieService: MovieService,
    private val theaterService: TheaterService
) {

    fun getById(id: Long): Showtime =
        showtimeRepository.findById(id).orElseThrow { ResourceNotFoundException("Showtime Not found") }

    fun add(request: ShowtimeRequest) {
        val movie = movieService.getById(request.movieId)
        val theater = theaterService.getById(request.theaterId)
        showtimeRepository.save(
            request.toShowtime(movie, theater)
        )
    }

    // del a showtime
    // cancel all bookings on that showtime
    fun deleteById(id: Long) {
        showtimeRepository.deleteById(id)

    }

    fun updateSeatsOnBookingById(id: Long, requestedSeats: Int) {
        val showtime = getById(id)
        showtimeRepository.save(
            showtime.copy(
                availableSeats = showtime.availableSeats - requestedSeats
            )
        )
//        TODO: Put a check for availableSeats >= requestedSeats
    }

    fun updateSeatsOnCancellationById(id: Long, requestedSeats: Int) {
        val showtime = getById(id)
        showtimeRepository.save(
            showtime.copy(
                availableSeats = showtime.availableSeats + requestedSeats
            )
        )
    }
}