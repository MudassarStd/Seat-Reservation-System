package com.std.seat_reservation.config

import com.std.seat_reservation.model.Movie
import com.std.seat_reservation.model.Role
import com.std.seat_reservation.model.Showtime
import com.std.seat_reservation.model.Theater
import com.std.seat_reservation.model.User
import com.std.seat_reservation.repository.MovieRepository
import com.std.seat_reservation.repository.ShowtimeRepository
import com.std.seat_reservation.repository.TheaterRepository
import com.std.seat_reservation.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import java.time.LocalTime

@Configuration
class DataSeeder {

    @Bean
    fun seedData(
        userRepository: UserRepository,
//        movieRepository: MovieRepository,
//        theaterRepository: TheaterRepository,
//        showtimeRepository: ShowtimeRepository,
        bCryptPasswordEncoder: BCryptPasswordEncoder
    ) = CommandLineRunner {

//        movieRepository.deleteAll()
//        theaterRepository.deleteAll()
//        showtimeRepository.deleteAll()

//        userRepository.deleteAll()
        // 1. Seed Admin User
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

        // 2. Clean existing data (Movies, Theaters, Showtimes) to avoid duplicates

        // 3. Seed Movies
//        val movies = listOf(
//            Movie(
//                id = 1,
//                title = "Neon Horizon",
//                description = "In a post-apocalyptic city, a mechanic discovers a secret that could reboot humanity.",
//                genre = "Science Fiction",
//                duration = "2h 15m",
//                rating = 7.9f,
//                releaseDate = LocalDate.parse("2025-06-01")
//            ),
//            Movie(
//                id = 2,
//                title = "Quantum Drift",
//                description = "A physicist caught in a time experiment must fix the timeline to save her family.",
//                genre = "Science Fiction",
//                duration = "2h 12m",
//                rating = 8.3f,
//                releaseDate = LocalDate.parse("2025-06-10")
//            ),
//            Movie(
//                id = 3,
//                title = "The Forgotten Code",
//                description = "A young programmer uncovers encrypted secrets tied to an underground society.",
//                genre = "Thriller",
//                duration = "2h 5m",
//                rating = 8.1f,
//                releaseDate = LocalDate.parse("2021-05-22")
//            ),
//            Movie(
//                id = 4,
//                title = "Wild Ember",
//                description = "An ex-firefighter must confront his past during a raging wildfire.",
//                genre = "Action",
//                duration = "1h 45m",
//                rating = 6.9f,
//                releaseDate = LocalDate.parse("2020-08-14")
//            ),
//            Movie(
//                id = 5,
//                title = "Echoes of Andromeda",
//                description = "Astronauts aboard a deep-space mission uncover a signal from an ancient alien race.",
//                genre = "Science Fiction",
//                duration = "2h 18m",
//                rating = 8.5f,
//                releaseDate = LocalDate.parse("2022-03-10")
//            ),
//            Movie(
//                id = 6,
//                title = "Paper Crown",
//                description = "A young girl rises to lead a revolution in a corrupt medieval kingdom.",
//                genre = "Fantasy",
//                duration = "2h 10m",
//                rating = 7.7f,
//                releaseDate = LocalDate.parse("2019-10-27")
//            ),
//            Movie(
//                id = 7,
//                title = "Gridlocked",
//                description = "A heist crew finds themselves trapped in a smart city controlled by a rogue AI.",
//                genre = "Action",
//                duration = "1h 52m",
//                rating = 7.9f,
//                releaseDate = LocalDate.parse("2024-06-30")
//            ),
//            Movie(
//                id = 8,
//                title = "Whispers Beneath",
//                description = "Paranormal investigators explore an ancient underground city with a deadly secret.",
//                genre = "Horror",
//                duration = "1h 47m",
//                rating = 6.5f,
//                releaseDate = LocalDate.parse("2018-09-12")
//            ),
//            Movie(
//                id = 9,
//                title = "The Painted Silence",
//                description = "A deaf painter becomes entangled in an art-world conspiracy.",
//                genre = "Drama",
//                duration = "2h 2m",
//                rating = 8.0f,
//                releaseDate = LocalDate.parse("2023-01-19")
//            ),
//            Movie(
//                id = 10,
//                title = "Midnight Cargo",
//                description = "A smuggler's final job spirals into chaos when the cargo turns out to be alive.",
//                genre = "Thriller",
//                duration = "1h 59m",
//                rating = 7.4f,
//                releaseDate = LocalDate.parse("2020-04-17")
//            )
//        )
//
//        movieRepository.saveAll(movies)
//        println("Movies seeded.")
//
//        // 4. Seed Theaters
//        val theaters = listOf(
//            Theater(id = 1, name = "Grand Vista Cinema", location = "Downtown LA", seats = 250),
//            Theater(id = 2, name = "Skyline Theater", location = "New York City", seats = 180),
//            Theater(id = 3, name = "Crescent Bay Multiplex", location = "San Francisco", seats = 300),
//            Theater(id = 4, name = "Riverside 10", location = "Chicago", seats = 200),
//            Theater(id = 5, name = "Sunset Plaza Theater", location = "Los Angeles", seats = 220),
//            Theater(id = 6, name = "Beacon Hall Cinemas", location = "Boston", seats = 150),
//            Theater(id = 7, name = "Crystal View Theater", location = "Miami", seats = 180),
//            Theater(id = 8, name = "Golden Gate Theater", location = "San Francisco", seats = 250),
//            Theater(id = 9, name = "Silver Screen Stadium", location = "Las Vegas", seats = 350),
//            Theater(id = 10, name = "Downtown Drive-In", location = "Seattle", seats = 100)
//        )
//        theaterRepository.saveAll(theaters)
//        println("Theaters seeded.")
//
//        // 5. Seed Showtimes
//        val showtimes = listOf(
//            Showtime(
//                movie = movies[0],  // Link to the first movie
//                theater = theaters[0],  // Link to the first theater
//                date = LocalDate.parse("2025-06-01"),
//                startTime = LocalTime.parse("14:00:00"),
//                endTime = LocalTime.parse("16:15:00"),
//                availableSeats = 200
//            ),
//            Showtime(
//                movie = movies[1],  // Link to the second movie
//                theater = theaters[1],  // Link to the second theater
//                date = LocalDate.parse("2025-06-02"),
//                startTime = LocalTime.parse("18:00:00"),
//                endTime = LocalTime.parse("20:12:00"),
//                availableSeats = 150
//            ),
//            Showtime(
//                movie = movies[2],  // Link to the third movie
//                theater = theaters[2],  // Link to the third theater
//                date = LocalDate.parse("2025-06-03"),
//                startTime = LocalTime.parse("16:00:00"),
//                endTime = LocalTime.parse("18:05:00"),
//                availableSeats = 170
//            ),
//            Showtime(
//                movie = movies[3],  // Link to the fourth movie
//                theater = theaters[3],  // Link to the fourth theater
//                date = LocalDate.parse("2025-06-04"),
//                startTime = LocalTime.parse("20:00:00"),
//                endTime = LocalTime.parse("21:45:00"),
//                availableSeats = 180
//            ),
//            Showtime(
//                movie = movies[4],  // Link to the fifth movie
//                theater = theaters[4],  // Link to the fifth theater
//                date = LocalDate.parse("2025-06-05"),
//                startTime = LocalTime.parse("10:00:00"),
//                endTime = LocalTime.parse("12:18:00"),
//                availableSeats = 220
//            )
//        )
//
//        showtimeRepository.saveAll(showtimes)
//        println("Showtimes seeded.")
    }
}
