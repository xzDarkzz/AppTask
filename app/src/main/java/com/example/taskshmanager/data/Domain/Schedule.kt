package com.example.taskshmanager.data.Domain

data class Schedule(
    val id: Int = 0,
    val subject: String,
    val description: String,
    val day: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long
)