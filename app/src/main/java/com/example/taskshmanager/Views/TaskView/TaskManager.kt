package com.example.taskshmanager.Views.TaskView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TaskManagerInformation: ViewModel() {

    var id by mutableStateOf(0)
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    fun UpdateInfo(newTitle: String, newDescription: String, idOfTheTask: Int = 0){
        id = idOfTheTask
        title = newTitle
        description = newDescription

    }
    fun EmptyInfo(){
        title = ""
        description = ""
        id = 0
    }

}