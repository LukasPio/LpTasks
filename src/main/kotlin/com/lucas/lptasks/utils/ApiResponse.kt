package com.lucas.lptasks.utils

data class ApiResponse<T>(
    val body: T?,
    val message: String,
    val statusCode: Int,
)
