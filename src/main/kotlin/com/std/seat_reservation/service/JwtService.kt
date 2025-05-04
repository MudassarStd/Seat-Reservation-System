package com.std.seat_reservation.service

import com.std.seat_reservation.util.JWT_SECRET_KEY
import com.std.seat_reservation.util.TOKEN_EXPIRATION
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {

    private val logger = LoggerFactory.getLogger(JwtService::class.java)

    fun generateToken(email: String): String {

        return Jwts.builder()
            .setSubject(email)
//            .addClaims(roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
            .signWith(
                Keys.hmacShaKeyFor(JWT_SECRET_KEY.toByteArray()),
                SignatureAlgorithm.HS256
            )
            .compact()
    }


    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.toByteArray()))
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            logger.error("Failed to validate token ${e.message}")
            return false
        }
    }

    fun extractEmail(token: String): String? {
        return extractClaims(token)?.subject
    }

    fun extractRole(token: String) = extractClaims(token)?.get("role") as? String

    private fun extractClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            logger.error("Failed to extract claim, eMessage: ${e.message}")
            null
        }
    }
}