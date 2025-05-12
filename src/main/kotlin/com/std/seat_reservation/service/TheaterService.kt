package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Theater
import com.std.seat_reservation.repository.TheaterRepository
import org.springframework.stereotype.Service

@Service
class TheaterService(
    private val theaterRepository: TheaterRepository
) {

    fun add(theater: Theater) = theaterRepository.save(theater)
    fun getAll() = theaterRepository.findAll()
    fun update(id: Long, theater: Theater) {
        theaterRepository.save(
            getById(id).copy(
                name = theater.name,
                location = theater.location,
                seats = theater.seats
            )
        )
    }


//    fun delete()

    fun getById(id: Long) = theaterRepository.findById(id).orElseThrow { ResourceNotFoundException("Not found") }
}