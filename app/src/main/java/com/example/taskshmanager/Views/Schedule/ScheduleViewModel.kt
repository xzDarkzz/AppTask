package com.example.taskshmanager.Views.Schedule

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskshmanager.data.Domain.Schedule
import com.example.taskshmanager.data.ScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel(
    private val repository: ScheduleRepository
) : ViewModel() {

    val scheduleState: StateFlow<List<Schedule>> = repository.getAllSchedules()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun addSchedule(
        subject: String,
        description: String,
        day: String,
        startTimeMillis: Long,
        endTimeMillis: Long,
        context: Context,
        repetDays: String
    ) {
        var startTimeMillis = startTimeMillis
        viewModelScope.launch {
            val (changeDate,nextAlarm) = NotificationManagerSchedule(startTimeMillis,repetDays).getNextStartAlarm()
            var nextNotification: Long
            if(changeDate){
                startTimeMillis = nextAlarm
                Log.d("CambioDeHora", "${startTimeMillis}")
                nextNotification = nextAlarm - 15*60*1000L
            }
            else{
                nextNotification = nextAlarm
            }
            val newSchedule = Schedule(
                subject = subject,
                description = description,
                day = day,
                startTimeMillis = startTimeMillis,
                endTimeMillis = endTimeMillis,
                repetDays = repetDays

            )
            val id = repository.insertSchedule(newSchedule).toInt()
            AlarmManagerSchedule.scheduleClassAlarm(context,id,nextNotification)

        }
    }


    fun editSchedule(
        id: Int,
        subject: String,
        description: String,
        day: String,
        startTimeMillis: Long,
        endTimeMillis: Long,
        context: Context,
        repetDays: String
    ) {
        var startTimeMillis = startTimeMillis
        viewModelScope.launch {
            val (changeDate,nextAlarm) = NotificationManagerSchedule(startTimeMillis,repetDays).getNextStartAlarm()
            var nextNotification: Long
            if(changeDate){
                startTimeMillis = nextAlarm
                nextNotification = nextAlarm - 15*60*1000L
            }
            else{
                nextNotification = nextAlarm
            }
            val updatedSchedule = Schedule(
                id = id,
                subject = subject,
                description = description,
                day = day,
                startTimeMillis = startTimeMillis,
                endTimeMillis = endTimeMillis,
                repetDays = repetDays
            )
            repository.update(updatedSchedule)
            AlarmManagerSchedule.scheduleClassAlarm(context,id,nextNotification)


        }
    }

    fun deleteSchedule(schedule: Schedule, context: Context) {
        viewModelScope.launch {
            repository.delete(schedule)

        }
    }
}

class ScheduleViewModelFactory(
    private val repository: ScheduleRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}