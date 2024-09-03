package com.lucas.lptasks.utils

import com.lucas.lptasks.enum.TaskCategory
import com.lucas.lptasks.enum.TaskPriority
import com.lucas.lptasks.exception.InvalidOrderSortException
import org.springframework.stereotype.Component

@Component
class TaskDataValidator {
    fun isValidPriority(priority: String): Boolean {
        val priorities = TaskPriority.entries.toTypedArray()
        for (validPriority in priorities) {
            if (validPriority.toString().lowercase() == priority.lowercase()) {
                return true
            }
        }
        return false
    }

    fun isValidOrderSort(orderSort: String): Boolean {
        if (orderSort.lowercase() == "asc" || orderSort.lowercase() == "desc") {
            return true
        }
        throw InvalidOrderSortException()
    }

    fun isValidCategory(category: String): Boolean {
        val categories = TaskCategory.entries.toTypedArray()
        for (validCategory in categories) {
            if (validCategory.toString().lowercase() == category.lowercase()) {
                return true
            }
        }
        return false
    }
}
