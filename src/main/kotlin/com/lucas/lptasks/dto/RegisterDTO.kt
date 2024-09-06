package com.lucas.lptasks.dto

data class RegisterDTO(
    val email: String,
    val password: String,
    val isAdmin: Boolean,
)
