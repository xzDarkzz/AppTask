package com.example.taskshmanager.data.Domain


data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val day: String,
    val dayOfTheMonth : String,
    val month: String,
    val year : String,
    val hour : String,
    val minute : String
) {
}