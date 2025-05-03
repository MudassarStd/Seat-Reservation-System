package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody authRequest: AuthRequest
    ) = authService.register(authRequest)

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest
    ) = authService.login(authRequest)
}