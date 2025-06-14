package com.std.seat_reservation.dto

import com.std.seat_reservation.model.Notification
import com.std.seat_reservation.model.User
import java.time.LocalDateTime

data class NotificationRequest(
    val text: String,
    val userEmail: String
)

data class NotificationResponse(
    val id: Long,
    val text: String,
    val timestamp: LocalDateTime,
    val unread: Boolean
)


fun NotificationRequest.toNotification(user: User) = Notification(
    text = text,
    user = user
)
fun Notification.toResponse() = NotificationResponse(
    text = text,
    id = id,
    timestamp = timeStamp,
    unread = unread
)