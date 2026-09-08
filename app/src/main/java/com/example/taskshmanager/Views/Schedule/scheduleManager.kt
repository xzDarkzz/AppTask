package com.example.taskshmanager.Views.Schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class scheduleManager: ViewModel() {

    var id by mutableStateOf(0)
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var daySelection by mutableStateOf("12345")

    fun UpdateInfo(newTitle: String, newDescription: String, idOfTheTask: Int = 0, repetDaySelection: String = ""){
        id = idOfTheTask
        title = newTitle
        description = newDescription
        daySelection = repetDaySelection
    }
    fun EmptyInfo(){
        title = ""
        description = ""
        id = 0
        daySelection = "12345"
    }

}
