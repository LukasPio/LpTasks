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
            if (task.isEmpty) {
                return ApiResponse.noTaskFoundById(id)
            }
            return ApiResponse.success(task.get().toDTO(), "Successfully retrieved task: ${task.get().id}")
        } catch (e: IllegalArgumentException) {
            return ApiResponse.invalidUUID()
        }
    }

    fun saveTask(taskData: List<TaskRequestDTO>): ApiResponse<out Any?> =
        try {
            taskData.forEach { (taskRepository.save(it.toTask())) }
            ApiResponse.created("Successfully saved all tasks")
        } catch (e: DataAccessException) {
            ApiResponse.internalError("Internal error while saving task")
        }

    fun saveTask(taskData: TaskRequestDTO): ApiResponse<out Any?> {
        val task: Task = taskData.toTask()
        taskRepository.save(task)
        return ApiResponse.success(task.toDTO(), "Successfully saved task: ${task.id}")
    }
}
