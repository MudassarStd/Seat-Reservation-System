package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.dto.AuthResponse
import com.std.seat_reservation.service.AuthService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun register(
        @RequestBody authRequest: AuthRequest
    ): AuthResponse {
        logger.info("Got in registration controller with request $authRequest")
        return authService.register(authRequest)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest
    ): AuthResponse {
        logger.info("Got in login controller with request $authRequest")
        return authService.login(authRequest)
    }
}