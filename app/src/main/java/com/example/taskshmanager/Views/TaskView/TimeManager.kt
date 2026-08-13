package com.example.taskshmanager.Views.TaskView

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
open class TimeManager : ViewModel(){

    private var currentTime = LocalDateTime.now()

    val currentDayIndex: Int
    init {
        currentDayIndex = when(currentTime.dayOfWeek){
            DayOfWeek.MONDAY -> 0
            DayOfWeek.TUESDAY -> 1
            DayOfWeek.WEDNESDAY -> 2
            DayOfWeek.THURSDAY -> 3
            DayOfWeek.FRIDAY -> 4
            DayOfWeek.SATURDAY -> 5
            DayOfWeek.SUNDAY -> 6
        }
    }

    var year by mutableStateOf(currentTime.year)


    //Calendar
    var month by mutableStateOf(currentTime.monthValue)
    var dayOfMonth by mutableStateOf(currentTime.dayOfMonth)
    var dayOfWeek by mutableStateOf(currentTime.dayOfWeek)
    //Clock
    var hour by mutableStateOf(currentTime.hour)
    var minute by mutableStateOf(currentTime.minute)




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

    fun CheckIndex(): Int {
        when(dayOfWeek){
            DayOfWeek.MONDAY -> return 0
            DayOfWeek.TUESDAY -> return 1
            DayOfWeek.WEDNESDAY -> return 2
            DayOfWeek.THURSDAY -> return 3
            DayOfWeek.FRIDAY -> return 4
            DayOfWeek.SATURDAY -> return 5
            DayOfWeek.SUNDAY -> return 6
        }
    }

}




class ComposableTimeManager: ViewModel(){
    var showAlert by mutableStateOf(false)
    fun openBottomSheet() { showAlert = true }
    fun closeBottomSheet() { showAlert = false }
}
