package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.NotificationRequest
import com.std.seat_reservation.service.NotificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping
    fun add(request: NotificationRequest) = notificationService.add(request)

    @GetMapping
    fun getAllByUser() = notificationService.getAllByUser()

    @GetMapping("/unread-count")
    fun getUnreadCountUserId() = notificationService.getUnreadCount()

    @PutMapping("/{id}")
    fun markAsRead(@PathVariable id: Long) = notificationService.markAsRead(id)

    @PutMapping
    fun markAllAsRead() = notificationService.markAllAsRead()
}