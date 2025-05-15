package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.model.Review
import com.std.seat_reservation.model.User
import com.std.seat_reservation.repository.MovieRepository
import com.std.seat_reservation.repository.ReviewRepository
import com.std.seat_reservation.repository.ShowtimeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MovieService(
    private val movieRepository: MovieRepository,
    private val showtimeRepository: ShowtimeRepository
) {
    fun add(movie: Movie): String {
        movieRepository.save(movie)
        return "Added"
    }

    fun getAll(genre: String?, rating: Float?, releaseDate: LocalDate?): List<Movie> {
        return movieRepository.findAll().filter { movie ->
            (genre == null || movie.genre == genre) &&
            (rating == null || movie.rating >= rating) &&
            (releaseDate == null || movie.releaseDate == releaseDate)
        }
    }

    fun getById(id: Long) = movieRepository.findById(id).orElseThrow { ResourceNotFoundException("Movie Not found for given id") }

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
        val movie = movieRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Movie with ID $id does not exist.")
        }
        movieRepository.deleteById(id)
        return "Deleted Successfully"
    }

    fun addBatch(movies: List<Movie>) = movieRepository.saveAll(movies)


//    fun getRecommendations(): List<Movie> {
//        return emptyList()
//    }

//     TODO: update booking status to cancellation after deletion, MAKE this ATOMIC operation

}