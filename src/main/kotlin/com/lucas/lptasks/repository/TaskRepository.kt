package com.lucas.lptasks.repository

import com.lucas.lptasks.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TaskRepository : JpaRepository<Task, UUID> {
    fun findByTitle(title: String): List<Task>

    fun findByCategory(category: String): List<Task>
}
