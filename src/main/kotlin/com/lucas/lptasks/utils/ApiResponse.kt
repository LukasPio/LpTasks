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

        fun internalError(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 500)

        fun unauthorized(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 401)

        private fun badRequest(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 400)

        fun invalidUUID(): ApiResponse<Unit> = badRequest("Invalid UUID")

        fun noTaskFoundById(id: String): ApiResponse<Unit> = badRequest("No task found with id: $id")
    }
}
