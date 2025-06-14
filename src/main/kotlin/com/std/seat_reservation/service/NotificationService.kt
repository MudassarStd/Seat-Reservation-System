package com.std.seat_reservation.service

import com.std.seat_reservation.dto.NotificationRequest
import com.std.seat_reservation.dto.toNotification
import com.std.seat_reservation.dto.toResponse
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.model.Notification
import com.std.seat_reservation.model.User
import com.std.seat_reservation.repository.NotificationRepository
import com.std.seat_reservation.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService
) {
    // add
    // getAllByUser
    // markAsRead
    // getUnreadCount

    fun add(request: NotificationRequest) {
        val user = userRepository.findByEmail(request.userEmail) ?: throw ResourceNotFoundException("User not found")
        notificationRepository.save(request.toNotification(user))
    }

    fun add(text: String, user: User) = notificationRepository.save(Notification(text = text, user = user))

    fun markAsRead(id: Long) {
        val notification = notificationRepository.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
        if (notification.user != authService.getCurrentAuthenticatedUser()) return
        notificationRepository.save(
            notification.copy(
                unread = false
            )
        )
    }

    fun markAllAsRead() {
        notificationRepository.findAllByUser(authService.getCurrentAuthenticatedUser()).filter {
            it.unread
        }
        .forEach { notification ->
            notificationRepository.save(notification.copy(
                unread = false
            ))
        }
    }

    fun getAllByUser() = notificationRepository.findAllByUser(authService.getCurrentAuthenticatedUser()).map { it.toResponse() }

    fun getUnreadCount(): Int = notificationRepository.findAllByUser(authService.getCurrentAuthenticatedUser()).filter { it.unread }.size

}