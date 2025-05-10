package com.std.seat_reservation.service

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Booking
import com.std.seat_reservation.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bookingService: BookingService
) {
    fun getAll() = userRepository.findAll()

    fun getUserBookings(id: Long) = bookingService.getBookingsByUserId(id)

    fun getById(id: Long) = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User does not exist") }

    fun updateAccessById(id: Long, isAllowed: Boolean) {
        userRepository.save(getById(id).copy(
            isAllowed = isAllowed
        ))
    }

    @Transactional
    fun deleteUser(id: Long): String {
        userRepository.deleteById(id)
        bookingService.cancelAllByUserId(userId = id)
        return "User deleted successfully"
    }

//    fun getUserBookings(userId: Long): List<Booking> = bookingService.getBookingsByUserId(userId)
}