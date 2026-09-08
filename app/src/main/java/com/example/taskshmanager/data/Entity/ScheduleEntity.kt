package com.example.taskshmanager.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subject: String,
    val description: String, // Profesor o Aula
    val day: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long,
    val repetDays: String
)