package com.std.seat_reservation.service

import com.std.seat_reservation.dto.ShowtimeRequest
import com.std.seat_reservation.dto.ShowtimeResponse
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toResponse
import com.std.seat_reservation.mapper.toShowtime
import com.std.seat_reservation.model.Showtime
import com.std.seat_reservation.repository.ShowtimeRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service

@Service
class ShowtimeService(
    private val showtimeRepository: ShowtimeRepository,
    private val movieService: MovieService,
    private val theaterService: TheaterService
) {

    fun getById(id: Long): Showtime =
        showtimeRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Showtime with ID $id not found.")
        }

    fun getAll(): List<ShowtimeResponse> =
        showtimeRepository.findAll().map { it.toResponse() }

    fun add(request: ShowtimeRequest) {
        val movie = movieService.getById(request.movieId)
//        val theater = theaterService.getById(request.theaterId)
        val showtime = request.toShowtime(movie, request.theaterName)
        showtimeRepository.save(showtime)
    }

    fun deleteById(id: Long) {
        if (!showtimeRepository.existsById(id)) {
            throw ResourceNotFoundException("Showtime with ID $id not found.")
        }

        showtimeRepository.deleteById(id)
    }
    // TODO: Cancel all bookings associated with this showtime

    fun update(id: Long, request: ShowtimeRequest) {
        val existingShowtime = getById(id)
        val movie = movieService.getById(request.movieId)

        val updatedShowtime = existingShowtime.copy(
            movie = movie,
            theater = request.theaterName,
            availableSeats = request.availableSeats,
            date = request.date,
            startTime = request.startTime,
            endTime = request.endTime
        )

        showtimeRepository.save(updatedShowtime)
    }

    fun updateSeatsOnBookingById(id: Long, requestedSeats: Int) {
        val showtime = getById(id)

        if (requestedSeats > showtime.availableSeats) {
            throw BadRequestException("Not enough seats available. Only ${showtime.availableSeats} left.")
        }

        showtimeRepository.save(
            showtime.copy(
                availableSeats = showtime.availableSeats - requestedSeats
            )
        )
    }

    fun updateSeatsOnCancellationById(id: Long, requestedSeats: Int) {
        val showtime = getById(id)
        val newSeatCount = showtime.availableSeats + requestedSeats

        showtimeRepository.save(
            showtime.copy(
                availableSeats = newSeatCount
            )
        )
    }
}

//import com.std.seat_reservation.dto.ShowtimeRequest
//import com.std.seat_reservation.dto.ShowtimeResponse
//import com.std.seat_reservation.exception.ResourceNotFoundException
//import com.std.seat_reservation.mapper.toResponse
//import com.std.seat_reservation.mapper.toShowtime
//import com.std.seat_reservation.model.Showtime
//import com.std.seat_reservation.repository.ShowtimeRepository
//import org.springframework.stereotype.Service
//
//@Service
//class ShowtimeService(
//    private val showtimeRepository: ShowtimeRepository,
//    private val movieService: MovieService,
//    private val theaterService: TheaterService
//) {
//
//    fun getById(id: Long): Showtime =
//        showtimeRepository.findById(id).orElseThrow { ResourceNotFoundException("Showtime Not found") }
//
//    fun add(request: ShowtimeRequest) {
//        val movie = movieService.getById(request.movieId)
////        val theater = theaterService.getById(request.theaterId)
//        showtimeRepository.save(
//            request.toShowtime(movie, request.theaterName)
//        )
//    }
//
//    fun getAll(): List<ShowtimeResponse> = showtimeRepository.findAll().map { it.toResponse() }
//
//    // del a showtime
//    // cancel all bookings on that showtime
//    fun deleteById(id: Long) {
//        showtimeRepository.deleteById(id)
//
//    }
//
//    fun updateSeatsOnBookingById(id: Long, requestedSeats: Int) {
//        val showtime = getById(id)
//        showtimeRepository.save(
//            showtime.copy(
//                availableSeats = showtime.availableSeats - requestedSeats
//            )
//        )
////        TODO: Put a check for availableSeats >= requestedSeats
//    }
//
//    fun updateSeatsOnCancellationById(id: Long, requestedSeats: Int) {
//        val showtime = getById(id)
//        showtimeRepository.save(
//            showtime.copy(
//                availableSeats = showtime.availableSeats + requestedSeats
//            )
//        )
//    }
//}