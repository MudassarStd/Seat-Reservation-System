package com.std.seat_reservation.service

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.dto.AuthResponse
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toUser
import com.std.seat_reservation.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authManager: AuthenticationManager,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun register(request: AuthRequest): AuthResponse {
        userRepository.save(request.toUser(bCryptPasswordEncoder.encode(request.password)))
        return AuthResponse(token = jwtService.generateToken(request.email))
    }

    fun login(request: AuthRequest): AuthResponse {
        val authToken = UsernamePasswordAuthenticationToken(request.email, request.password)
        authManager.authenticate(authToken)
        return AuthResponse(token = jwtService.generateToken(request.email))
    }

    fun getCurrentAuthenticatedUser() = SecurityContextHolder.getContext().authentication as Principal
}