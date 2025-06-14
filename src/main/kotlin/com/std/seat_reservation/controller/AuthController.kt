package com.std.seat_reservation.controller

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.dto.AuthResponse
import com.std.seat_reservation.dto.PasswordResetRequest
import com.std.seat_reservation.dto.ProfileUpdateRequest
import com.std.seat_reservation.service.AuthService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
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

    @PostMapping("/update-profile")
    fun updateProfile(
        @RequestBody request: ProfileUpdateRequest
    ) = authService.updateProfile(request)

    @PostMapping("/change-password")
    fun changePassword(
        @RequestBody request: PasswordResetRequest
    ): AuthResponse {
        logger.info("Got in password reset controller with request $request")
        return authService.changePassword(request)
    }
}