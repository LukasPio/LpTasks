package com.lucas.lptasks.model
import com.lucas.lptasks.dto.TaskResponseDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @Column(name = "id", updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
) {
    fun toDTO(): TaskResponseDTO =
        TaskResponseDTO(
            id.toString(),
            title,
            description,
            category,
            priority,
        )
}
