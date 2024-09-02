package com.lucas.lptasks.service

import com.lucas.lptasks.dto.TaskResponseDTO
import com.lucas.lptasks.utils.ApiResponse
import org.springframework.stereotype.Service

@Service
class ResponseService {
    fun <T> success(
        body: T,
        message: String,
    ): ApiResponse<T> = ApiResponse(body, message, 200)

    fun created(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 201)

    fun noContent(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 204)

    fun internalError(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 500)

    fun notFound(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 404)

    fun badRequest(message: String): ApiResponse<Unit> = ApiResponse(Unit, message, 400)

    fun taskNotFound(): ApiResponse<Unit> = notFound("No task found")

    fun fetchedAllTasks(tasks: List<TaskResponseDTO>): ApiResponse<List<TaskResponseDTO>> =
        ApiResponse(tasks, "Successfully fetched ${tasks.size} tasks", 200)
}
