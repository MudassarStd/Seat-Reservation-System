package com.std.seat_reservation.config

import com.std.seat_reservation.model.Role
import com.std.seat_reservation.model.User
import com.std.seat_reservation.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class DataSeeder {
    @Bean
    fun seedAdmin(userRepository: UserRepository, bCryptPasswordEncoder: BCryptPasswordEncoder) = CommandLineRunner {
        val existingAdmin = userRepository.findByEmail("admin@cineplex.com")
        if (existingAdmin == null) {
            val admin = User(
                email = "admin@cineplex.com",
                password = bCryptPasswordEncoder.encode("admin123"),
                role = Role.ADMIN
            )
            userRepository.save(admin)
            println("Admin user seeded.")
        } else {
            println("Admin already exists.")
        }
    }
}