package com.example.taskshmanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Calendar

open class TimeManager : ViewModel(){
    var currentTime = Calendar.getInstance()

    var date by mutableStateOf("${currentTime.get(Calendar.MONTH) + 1} / ${currentTime.get(Calendar.DAY_OF_MONTH)}")
    var time by mutableStateOf("${currentTime.get(Calendar.HOUR_OF_DAY)} : ${currentTime.get(Calendar.MINUTE)}")

    fun ChangeHour(newHour: String){
        time = newHour
    }
    fun ChangeDate(newDate: String){
        date = newDate
    }
    fun ReturnActualTime(){
        val newCurrentTime = Calendar.getInstance()
        time = "${newCurrentTime.get(Calendar.HOUR_OF_DAY)} : ${newCurrentTime.get(Calendar.MINUTE)}"
        date = "${newCurrentTime.get(Calendar.MONTH) + 1} / ${newCurrentTime.get(Calendar.DAY_OF_MONTH)}"
    }

}




class ComposableTimeManager: ViewModel(){
    var showAlert by mutableStateOf(false)
    fun openBottomSheet() { showAlert = true }
    fun closeBottomSheet() { showAlert = false }
}
