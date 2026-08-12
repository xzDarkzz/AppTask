package com.example.taskshmanager.Views.TaskView

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
open class TimeManager : ViewModel(){

    private var currentTime = LocalDateTime.now()


    var year by mutableStateOf(currentTime.year)
    //Calendar
    var month by mutableStateOf(currentTime.monthValue)
    var dayOfMonth by mutableStateOf(currentTime.dayOfMonth)
    var dayOfWeek by mutableStateOf(currentTime.dayOfWeek)
    //Clock
    var hour by mutableStateOf(currentTime.hour)
    var minute by mutableStateOf(currentTime.minute)




    @OptIn(ExperimentalMaterial3Api::class)
    fun ChangeHour(newHour: TimePickerState){
        hour = newHour.hour
        minute = newHour.minute

    }
    fun ChangeDate(newDate: LocalDate){
        year = newDate.year
        month = newDate.monthValue
        dayOfMonth = newDate.dayOfMonth
        dayOfWeek = newDate.dayOfWeek

    }
    fun ReturnActualTime(){
        currentTime = LocalDateTime.now()
        month = currentTime.monthValue
        dayOfWeek = currentTime.dayOfWeek
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
