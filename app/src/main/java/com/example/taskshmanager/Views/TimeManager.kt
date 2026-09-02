package com.example.taskshmanager.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
class TimeManager(
    initialDateTime: LocalDateTime = LocalDateTime.now()
) {
    private var currentTime = initialDateTime

    val currentDayIndex: Int = when (currentTime.dayOfWeek) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
        DayOfWeek.SATURDAY -> 5
        DayOfWeek.SUNDAY -> 6
    }

    var year by mutableStateOf(currentTime.year)
    var month by mutableStateOf(currentTime.monthValue)
    var dayOfMonth by mutableStateOf(currentTime.dayOfMonth)
    var dayOfWeek by mutableStateOf(currentTime.dayOfWeek)

    var hour by mutableStateOf(currentTime.hour)
    var minute by mutableStateOf(currentTime.minute)

    fun ChangeHour(newHour: TimePickerState) {
        hour = newHour.hour
        minute = newHour.minute
    }

    fun ChangeDate(newDate: LocalDate) {
        year = newDate.year
        month = newDate.monthValue
        dayOfMonth = newDate.dayOfMonth
        dayOfWeek = newDate.dayOfWeek
    }

    fun ReturnActualTime() {
        currentTime = LocalDateTime.now()
        year = currentTime.year
        month = currentTime.monthValue
        dayOfWeek = currentTime.dayOfWeek
        dayOfMonth = currentTime.dayOfMonth
        hour = currentTime.hour
        minute = currentTime.minute
    }

    fun CheckIndex(): Int = dayOfWeek.value - 1

    fun getSelectedLocalDateTime(): LocalDateTime {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute)
    }

    fun getSelectedTimeInMillis(): Long {
        return getSelectedLocalDateTime()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun setTimeFromMillis(millis: Long): LocalDateTime {
        val dateTime = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return dateTime

    }
}




class ComposableTimeManager: ViewModel(){
    var showAlert by mutableStateOf(false)
    fun openBottomSheet() { showAlert = true }
    fun closeBottomSheet() { showAlert = false }
}
