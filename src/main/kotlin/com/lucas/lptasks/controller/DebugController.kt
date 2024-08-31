package com.lucas.lptasks.controller

import com.lucas.lptasks.dto.TaskResponseDTO
import com.lucas.lptasks.model.Task
import com.lucas.lptasks.utils.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/dev")
class DebugController {
    @GetMapping("/hello")
    fun hello(): String {
        val messageToShow = "Hello World"
        return messageToShow
    }

    @GetMapping("/newTask")
    fun newTask(): ApiResponse<TaskResponseDTO> {
        val task =
            Task(
                UUID.randomUUID(),
                "Título da Tarefa",
                "Descrição da Tarefa",
                "Categoria da Tarefa",
                "Alta",
            )
        val response =
            ApiResponse(
                task.toDTO(),
                "generate new task.",
                200,
            )
        return response
    }
}
