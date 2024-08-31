package com.lucas.lptasks.service

import com.lucas.lptasks.dto.TaskRequestDTO
import com.lucas.lptasks.model.Task
import com.lucas.lptasks.repository.TaskRepository
import com.lucas.lptasks.utils.ApiResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository,
) {
    fun getAllTasks(): ApiResponse<out Any?> {
        val tasks = taskRepository.findAll().map { it.toDTO() }
        return if (tasks.isEmpty()) {
            ApiResponse.notFound("No tasks found")
        } else {
            ApiResponse.success(tasks, "Successfully retrieved ${tasks.size} tasks")
        }
    }

    fun getTaskById(id: String): ApiResponse<out Any?> {
        return try {
            val task = taskRepository.findById(UUID.fromString(id))
            return if (task.isEmpty) {
                ApiResponse.badRequest("Not saved task with id: $id")
            } else {
                ApiResponse.success(task.get().toDTO(), "Successfully retrieved ${task.get().id}")
            }
        } catch (e: IllegalArgumentException) {
            ApiResponse.badRequest("$id is an invalid UUID")
        }
    }

    fun saveTask(taskData: TaskRequestDTO): ApiResponse<out Any?> {
        val task: Task = taskData.toTask()
        taskRepository.save(task)
        return ApiResponse.success(task.toDTO(), "Successfully saved task: ${task.id}")
    }
}
