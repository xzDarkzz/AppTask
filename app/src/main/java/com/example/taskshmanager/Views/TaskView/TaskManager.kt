package com.example.taskshmanager.Views.TaskView

abstract class TaskManager(
    val title: String,
    val description: String,
    val day: String = "Monday"
){}

class SaveDataTask(
    title: String,
    description: String,
    day: String
): TaskManager(title,description,day){

    val saveTask = mapOf(
        "title" to title,
        "dsc" to description,
        "day" to day
    )


}