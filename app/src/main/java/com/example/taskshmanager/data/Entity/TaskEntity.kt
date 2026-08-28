package com.example.taskshmanager.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val day: String,
    val dueDateMillis : Long,
    val dueDateEndMillis : Long = 0L
){

}
