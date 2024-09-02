package com.lucas.lptasks.controller

import com.lucas.lptasks.dto.TaskRequestDTO
import com.lucas.lptasks.dto.TaskResponseDTO
import com.lucas.lptasks.service.ResponseService
import com.lucas.lptasks.service.TaskService
import com.lucas.lptasks.utils.ApiResponse
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/app/tasks")
class TaskController(
    private val taskService: TaskService,
    private val responseService: ResponseService,
) {
    @GetMapping
    fun getAllTasks(): ApiResponse<List<TaskResponseDTO>> {
        val tasks = taskService.getAllTasks()
        return responseService.fetchedAllTasks(tasks)
    }

    @GetMapping("/title")
    fun getTaskByName(
        @Valid @NotEmpty @RequestParam taskTitle: String,
    ): ApiResponse<List<TaskResponseDTO>> {
        val tasks = taskService.getTasksByName(taskTitle)
        return responseService.fetchedAllTasks(tasks)
    }

    @GetMapping("/id")
    fun getTaskById(
        @Valid @NotEmpty @RequestParam id: String,
    ): ApiResponse<TaskResponseDTO> {
        val task = taskService.getTaskById(id)
        return responseService.success(task, "Successfully got task")
    }

    @PostMapping
    fun saveTasks(
        @Valid @NotEmpty @RequestBody tasksToSave: List<TaskRequestDTO>,
    ): ApiResponse<Unit> {
        taskService.saveTasks(tasksToSave)
        return responseService.noContent("Tasks saved successfully")
    }

    @PutMapping("/{id}")
    fun updateTasks(
        @Valid @NotEmpty @RequestBody updateData: TaskRequestDTO,
        @Valid @NotEmpty @PathVariable id: String,
    ): ApiResponse<TaskResponseDTO> {
        val task = taskService.updateTask(updateData, id)
        return responseService.success(task, "Successfully updated task")
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deleteTask(
        @Valid @NotEmpty @PathVariable id: String,
    ): ApiResponse<Unit> {
        taskService.deleteTask(id)
        return responseService.noContent("Task deleted successfully")
    }
}
