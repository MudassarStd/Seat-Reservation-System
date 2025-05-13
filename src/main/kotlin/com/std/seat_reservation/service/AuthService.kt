package com.std.seat_reservation.service

import com.std.seat_reservation.dto.AuthRequest
import com.std.seat_reservation.dto.AuthResponse
import com.std.seat_reservation.dto.UserResponse
import com.std.seat_reservation.dto.toAuthResponse
import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.mapper.toUser
import com.std.seat_reservation.model.User
import com.std.seat_reservation.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import java.security.Principal
//@CrossOrigin(origins = ["http://localhost:3000"])
@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authManager: AuthenticationManager,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun register(request: AuthRequest): AuthResponse {
        val newUser = request.toUser(bCryptPasswordEncoder.encode(request.password))
        userRepository.save(newUser)
        return AuthResponse(token = jwtService.generateToken(request.email), newUser.toAuthResponse())
    }

    fun login(request: AuthRequest): AuthResponse {
        val authToken = UsernamePasswordAuthenticationToken(request.email, request.password)
        authManager.authenticate(authToken)
        val user = userRepository.findByEmail(request.email) ?: throw ResourceNotFoundException("User for ${request.email} does not exist")
        return AuthResponse(token = jwtService.generateToken(request.email), user.toAuthResponse())
    }

    fun getCurrentAuthenticatedUser() = SecurityContextHolder.getContext().authentication.principal as User
}