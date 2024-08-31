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

        fun created(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 201)

        fun notFound(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 404)

        fun <T> badRequest(message: String): ApiResponse<T> = ApiResponse(null, message, 400)
        fun internalError(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 500)
        fun unauthorized(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 401)
    }
}
