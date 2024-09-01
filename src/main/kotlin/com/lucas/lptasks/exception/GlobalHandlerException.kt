package com.lucas.lptasks.exception

import com.lucas.lptasks.utils.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalHandlerException {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageNotReadable(e: HttpMessageNotReadableException): ApiResponse<Unit> =
        ApiResponse.badRequest("Insufficient params were provided.")

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(e: ConstraintViolationException): ApiResponse<Unit> =
        ApiResponse.badRequest("All parameters are required.")

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ApiResponse<Unit> = ApiResponse.badRequest("Invalid parameters were provided.")

    @ExceptionHandler(DataAccessException::class)
    fun dataAccess(): ApiResponse<Unit> = ApiResponse.internalError("An error occurred with database manipulation")
}
