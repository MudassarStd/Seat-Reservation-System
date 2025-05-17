//package com.std.seat_reservation.controller
//
//import com.std.seat_reservation.dto.BookingRequest
//import com.std.seat_reservation.dto.ReviewRequest
//import com.std.seat_reservation.model.BookingStatus
//import com.std.seat_reservation.service.BookingService
//import com.std.seat_reservation.service.ReviewService
//import jakarta.validation.Valid
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/profile")
//class ProfileController(
//    private val reviewService: ReviewService,
//    private val bookingService: BookingService
//) {
//
//    // ------------------ Reviews ------------------
////    @GetMapping("/reviews")
////    fun getMyReviews() = reviewService.getMyReviews()
//
////    @DeleteMapping("/reviews/{id}")
////    fun deleteMyReview(@PathVariable id: Long) = reviewService.deleteReview(id)
////
////    @PutMapping("/reviews/{id}")
////    fun updateMyReview(
////        @PathVariable id: Long,
////        @RequestBody request: ReviewRequest
////    ) = reviewService.updateReview(id, request)
////
//
//    // ------------------ Bookings ------------------
//
//    @GetMapping("/bookings")
//    fun getMyBookings(@RequestParam status: BookingStatus? = null) =
//        bookingService.getMyBookings(status)
//
////    @PostMapping("/bookings")
////    fun createMyBooking(@Valid @RequestBody request: BookingRequest) =
////        bookingService.createMyBooking(request)
//
//    @PutMapping("/bookings")
//    fun updateMyBooking(@Valid @RequestBody request: BookingRequest) =
//        bookingService.updateMyBooking(request)
//
//    @DeleteMapping("/bookings/{id}")
//    fun deleteMyBooking(@PathVariable id: Long) =
//        bookingService.deleteMyBooking(id)
//
//    @DeleteMapping("/bookings/cancel/{id}")
//    fun cancelMyBooking(@PathVariable id: Long) =
//        bookingService.cancelMyBooking(id)
//}
