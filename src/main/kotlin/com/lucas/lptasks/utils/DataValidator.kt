package com.lucas.lptasks.utils

import com.lucas.lptasks.enum.TaskPriority

class DataValidator {
    companion object {
        fun isValidPriority(priority: String): Boolean {
            val priorities = TaskPriority.entries.toTypedArray()
            for (validPriority in priorities) {
                if (validPriority.value.equals(priority, ignoreCase = true)) {
                    return true
                }
            }
            return false
        }
    }
}
