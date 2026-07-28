package com.example.taskshmanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Calendar

open class TimeManager{
    var currentTime = Calendar.getInstance()
    var currentDate = "${currentTime.get(Calendar.MONTH) + 1}/${currentTime.get(Calendar.DAY_OF_MONTH)}"
    var currentHour = "${currentTime.get(Calendar.HOUR_OF_DAY)} : ${currentTime.get(Calendar.MINUTE)}"

}

class ComposableTimeManager: ViewModel(){
    var showAlert by mutableStateOf(false)
    fun openBottomSheet() { showAlert = true }
    fun closeBottomSheet() { showAlert = false }
}
