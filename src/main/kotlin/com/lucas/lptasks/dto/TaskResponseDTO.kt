package com.lucas.lptasks.dto

data class TaskResponseDTO(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
)
