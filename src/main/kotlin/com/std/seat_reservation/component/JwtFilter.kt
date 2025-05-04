package com.std.seat_reservation.component

import com.std.seat_reservation.exception.ResourceNotFoundException
import com.std.seat_reservation.service.CustomUserDetailsService
import com.std.seat_reservation.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val logger = LoggerFactory.getLogger(JwtFilter::class.java)

        val authHeader = request.getHeader("Authorization")
        logger.info("Intercepted Request header: $authHeader")

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response)

            logger.info("Header does not contain Bearer, returning call")
            return
        }

        val token = authHeader.substring(7).trim()
        logger.info("Intercepted Token: $token")
        val email = jwtService.extractEmail(token)
        logger.info("Extracted email from token: $email")

        email?.let {
            if (SecurityContextHolder.getContext().authentication == null)
            {
                val userDetails = userDetailsService.loadUserByUsername(it)

                if (jwtService.validateToken(token)) {
                    val authTokenObject = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                    SecurityContextHolder.getContext().authentication = authTokenObject
                } else {
                    throw IllegalArgumentException("Invalid jwt token: JwtFilter::class")
                }
            } else {
                filterChain.doFilter(request, response)
            }
        } ?: run {
            filterChain.doFilter(request, response)
            throw ResourceNotFoundException("Email not found: JwtFilter::class")
        }
        filterChain.doFilter(request, response)
    }
}