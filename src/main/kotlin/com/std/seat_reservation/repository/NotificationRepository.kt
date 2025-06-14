package com.std.seat_reservation.repository

import com.std.seat_reservation.dto.NotificationResponse
import com.std.seat_reservation.model.Notification
import com.std.seat_reservation.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
    fun findAllByUser(user: User): List<Notification>
    fun findAllByUserId(userId: Long): List<Notification>

}