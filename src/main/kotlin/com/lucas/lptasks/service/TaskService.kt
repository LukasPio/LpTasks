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
        val task = taskRepository.findById(UUID.fromString(id))
        return if (task.isEmpty) {
            ApiResponse.noTaskFoundById(id)
        } else {
            ApiResponse.success(task.get().toDTO(), "Successfully retrieved task: ${task.get().id}")
        }
    }

    fun saveTask(taskData: List<TaskRequestDTO>): ApiResponse<out Any?> {
        taskData.forEach { (taskRepository.save(it.toTask())) }
        return ApiResponse.created("Successfully saved all tasks")
    }

    fun deleteTask(id: String): ApiResponse<out Any?> {
        val uuid = UUID.fromString(id)
        val existsTask: Boolean = taskRepository.existsById(uuid)
        return if (existsTask) {
            taskRepository.deleteById(uuid)
            ApiResponse.success(null, "Successfully deleted task: $id")
        } else {
            ApiResponse.noTaskFoundById(id)
        }
    }

    fun updateTask(
        updateData: TaskRequestDTO,
        id: String,
    ): ApiResponse<out Any?> {
        val task = taskRepository.findById(UUID.fromString(id))
        if (task.isEmpty) {
            return ApiResponse.noTaskFoundById(id)
        }
        val taskToSave: Task = task.get()
        taskToSave.update(updateData)
        taskRepository.save(taskToSave)

        return ApiResponse.noContent("Successfully updated task: $id")
    }
}
