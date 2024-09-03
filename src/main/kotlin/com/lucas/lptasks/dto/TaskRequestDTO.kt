package com.lucas.lptasks.dto

import com.lucas.lptasks.model.Task
import jakarta.validation.constraints.NotEmpty
import java.util.Locale
import java.util.UUID

data class TaskRequestDTO(
    @field:NotEmpty(message = "Title is required")
    val title: String,
    @field:NotEmpty(message = "Description is required")
    val description: String,
    @field:NotEmpty(message = "Category is required")
    val category: String,
    @field:NotEmpty(message = "Priority is required")
    val priority: String,
) {
    fun toTask(): Task =
        Task(
            UUID.randomUUID(),
            title,
            description,
            category,
            priority.lowercase(Locale.getDefault()),
        )
}
