package com.lucas.lptasks.utils

data class ApiResponse<T>(
    val body: T?,
    val message: String,
    val statusCode: Int,
) {
    companion object {
        fun <T> success(
            body: T,
            message: String,
        ): ApiResponse<T> = ApiResponse(body, message, 200)

        fun <T> notFound(message: String): ApiResponse<T> = ApiResponse(null, message, 404)

        fun <T> badRequest(message: String): ApiResponse<T> = ApiResponse(null, message, 400)
    }
}
