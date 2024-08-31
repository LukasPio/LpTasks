package com.lucas.lptasks.controller

import com.lucas.lptasks.dto.TaskRequestDTO
import com.lucas.lptasks.service.TaskService
import com.lucas.lptasks.utils.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/tasks")
class TaskController(
    private val taskService: TaskService,
) {
    @GetMapping
    fun getTasks(): ApiResponse<out Any?> = taskService.getAllTasks()

    @GetMapping("/{id}")
    fun getTaskById(
        @PathVariable id: String,
    ): ApiResponse<out Any?> = taskService.getTaskById(id)

    @PostMapping
    fun saveTask(
        @RequestBody taskData: TaskRequestDTO,
    ): ApiResponse<out Any?> = taskService.saveTask(taskData)
    fun saveTasks(
        @RequestBody tasksToSave: List<TaskRequestDTO>,
    ): ApiResponse<out Any?> = taskService.saveTask(tasksToSave)

}
