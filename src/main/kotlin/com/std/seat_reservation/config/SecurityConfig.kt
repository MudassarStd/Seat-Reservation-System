package com.std.seat_reservation.config

import com.std.seat_reservation.component.JwtFilter
import com.std.seat_reservation.model.Role
import com.std.seat_reservation.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetails: CustomUserDetailsService,
    private val jwtFilter: JwtFilter
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authManager(config: AuthenticationConfiguration) = config.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.apply {
                    requestMatchers("/auth/**").permitAll()
                    requestMatchers(HttpMethod.POST, "/movies/**").hasRole(Role.ADMIN.name)
                    requestMatchers(HttpMethod.DELETE, "/movies/**").hasRole(Role.ADMIN.name)
                    requestMatchers(HttpMethod.GET, "/movies/**").hasAnyRole(Role.ADMIN.name, Role.USER.name)
                    anyRequest().authenticated()
                }
            }
            .userDetailsService(userDetails)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun cors(): WebMvcConfigurer = object: WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
        }
    }

}