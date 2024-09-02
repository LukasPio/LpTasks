package com.lucas.lptasks.service

import com.lucas.lptasks.dto.TaskRequestDTO
import com.lucas.lptasks.dto.TaskResponseDTO
import com.lucas.lptasks.exception.InvalidTaskPriorityException
import com.lucas.lptasks.exception.TaskNotFoundException
import com.lucas.lptasks.model.Task
import com.lucas.lptasks.repository.TaskRepository
import com.lucas.lptasks.utils.DataValidator
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository,
) {
    fun getAllTasks(): List<TaskResponseDTO> {
        val tasks = taskRepository.findAll().map { it.toDTO() }
        return tasks
    }

    fun getTaskById(id: String): TaskResponseDTO {
        val task = taskRepository.findById(UUID.fromString(id))
        if (task.isEmpty) {
            throw TaskNotFoundException()
        }
        return task.get().toDTO()
    }

    fun saveTasks(taskData: List<TaskRequestDTO>) {
        taskData.forEach {
            if (DataValidator.isValidPriority(it.priority)) {
                taskRepository.save(it.toTask())
            } else {
                throw InvalidTaskPriorityException()
            }
        }
    }

    fun deleteTask(id: String) {
        val uuid = UUID.fromString(id)
        val existsTask: Boolean = taskRepository.existsById(uuid)
        if (!existsTask) throw TaskNotFoundException()

        taskRepository.deleteById(uuid)
    }

    fun updateTask(
        updateData: TaskRequestDTO,
        id: String,
    ): TaskResponseDTO {
        val optionalTask = taskRepository.findById(UUID.fromString(id))

        if (optionalTask.isEmpty) throw TaskNotFoundException()
        if (!DataValidator.isValidPriority(updateData.priority)) throw InvalidTaskPriorityException()

        val task: Task = optionalTask.get()
        task.update(updateData)
        taskRepository.save(task)
        return task.toDTO()
    }

    fun getTasksByName(taskTitle: String): List<TaskResponseDTO> {
        val tasks = taskRepository.findByTitle(taskTitle)
        if (tasks.isEmpty()) throw TaskNotFoundException()
        return tasks.map { it.toDTO() }
    }
}
