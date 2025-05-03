package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {
    fun add(movie: Movie): String {
        movieRepository.save(movie)
        return "Ädded"
    }

    fun getAll(genre: String?, rating: Float?): List<Movie> = movieRepository.findAll().filter {
        (genre == null || it.genre == genre) && (rating == null || it.rating >= rating)
    }
    fun getById(id: Long) = movieRepository.findById(id).orElseThrow { ResourceNotFoundException("Movie Not found") }

    fun getByNameSearch(query: String) = movieRepository.findAll().filter { it.title.contains(query) }

    fun updateById(id: Long, movie: Movie) {
        val currentRecord = movieRepository.findById(id).orElseThrow { ResourceNotFoundException("Movie not found for passed Id") }
        movieRepository.save(
            currentRecord.copy(
                title = movie.title,
                description = movie.description,
                duration = movie.duration,
                rating = movie.rating,
                genre = movie.genre,
                releaseDate = movie.releaseDate
            )
        )
    }

    fun deleteById(id: Long): String {
        movieRepository.deleteById(id)
        // TODO: update booking status to cancellation after deletion, MAKE this ATOMIC operation
        // Booking must be cancelled if movie is deleted
        return "Deleted Successfully"
    }
}