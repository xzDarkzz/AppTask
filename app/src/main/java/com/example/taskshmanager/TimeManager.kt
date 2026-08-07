package com.example.taskshmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
open class TimeManager : ViewModel(){

    private var currentTime = LocalDateTime.now()

    //Calendar
    var month by mutableStateOf(currentTime.monthValue)
    var dayOfMonth by mutableStateOf(currentTime.dayOfMonth)
    var dayOfWeek by mutableStateOf(currentTime.dayOfWeek)
    //Clock
    var hour by mutableStateOf(currentTime.hour)
    var minute by mutableStateOf(currentTime.minute)


    var time by mutableStateOf("${hour} : ${minute}")

    fun ChangeHour(newHour: String){
        time = newHour
    }
    fun ChangeDate(newDate: LocalDate){
        month = newDate.monthValue
        dayOfMonth = newDate.dayOfMonth
        dayOfWeek = newDate.dayOfWeek

    }
    fun ReturnActualTime(){
        currentTime = LocalDateTime.now()
        month = currentTime.monthValue
        dayOfMonth = currentTime.dayOfMonth
        hour = currentTime.hour
        minute = currentTime.minute
    }

}




class ComposableTimeManager: ViewModel(){
    var showAlert by mutableStateOf(false)
    fun openBottomSheet() { showAlert = true }
    fun closeBottomSheet() { showAlert = false }
}
