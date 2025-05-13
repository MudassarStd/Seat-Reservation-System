package com.std.seat_reservation.exception

import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(req: HttpServletRequest, ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                status = 404,
                error = "Not Found",
                message = ex.message ?: "Resource not found",
                path = req.requestURI
            )
        )

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(req: HttpServletRequest, ex: BadRequestException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                status = 400,
                error = "Bad Request",
                message = ex.message ?: "Bad request",
                path = req.requestURI
            )
        )

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(req: HttpServletRequest, ex: IllegalStateException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                status = 409,
                error = "Illegal State",
                message = ex.message ?: "Illegal operation",
                path = req.requestURI
            )
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        req: HttpServletRequest,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid")
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                status = 400,
                error = "Validation Failed",
                message = "Invalid request data",
                path = req.requestURI,
                fieldErrors = fieldErrors
            )
        )
    }
}

data class ErrorResponse(
    val timestamp: String = LocalDateTime.now().toString(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String? = null,
    val fieldErrors: Map<String, String>? = null
)
