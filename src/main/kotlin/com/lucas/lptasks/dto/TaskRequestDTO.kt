package com.lucas.lptasks.dto

import com.lucas.lptasks.model.Task
import java.util.UUID

data class TaskRequestDTO(
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
) {
    fun toTask(): Task =
        Task(
            UUID.randomUUID(),
            title,
            description,
            category,
            priority,
        )
}
