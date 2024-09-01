package com.lucas.lptasks.controller

import com.lucas.lptasks.dto.TaskRequestDTO
import com.lucas.lptasks.service.TaskService
import com.lucas.lptasks.utils.ApiResponse
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
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/app/tasks")
class TaskController(
    private val taskService: TaskService,
) {
    @GetMapping
    fun getAllTasks(): ApiResponse<out Any?> = taskService.getAllTasks()

    @GetMapping("/{id}")
    fun getTaskById(
        @PathVariable id: String,
    ): ApiResponse<out Any?> = taskService.getTaskById(id)

    @PostMapping
    fun saveTasks(
        @Valid @NotEmpty @RequestBody tasksToSave: List<TaskRequestDTO>,
    ): ApiResponse<out Any?> = taskService.saveTasks(tasksToSave)

    @PutMapping("/{id}")
    fun updateTasks(
        @RequestBody updateData: TaskRequestDTO,
        @PathVariable id: String,
    ): ApiResponse<out Any?> = taskService.updateTask(updateData, id)

    @DeleteMapping("/{id}")
    fun deleteTask(
        @PathVariable id: String,
    ): ApiResponse<out Any?> = taskService.deleteTask(id)
}
