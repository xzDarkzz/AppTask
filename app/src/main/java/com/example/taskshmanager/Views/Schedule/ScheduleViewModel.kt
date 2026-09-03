package com.example.taskshmanager.Views.Schedule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskshmanager.data.Domain.Schedule
import com.example.taskshmanager.data.ScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
        context: Context
    ) {
        viewModelScope.launch {
            val newSchedule = Schedule(
                subject = subject,
                description = description,
                day = day,
                startTimeMillis = startTimeMillis,
                endTimeMillis = endTimeMillis
            )
            val id = repository.insertSchedule(newSchedule).toInt()

            // TODO: Agendar la alarma de la clase a los 15 y 5 minutos antes
        }
    }

    fun editSchedule(
        id: Int,
        subject: String,
        description: String,
        day: String,
        startTimeMillis: Long,
        endTimeMillis: Long,
        context: Context
    ) {
        viewModelScope.launch {
            val updatedSchedule = Schedule(
                id = id,
                subject = subject,
                description = description,
                day = day,
                startTimeMillis = startTimeMillis,
                endTimeMillis = endTimeMillis
            )
            repository.update(updatedSchedule)

            // TODO: Re-agendar la alarma con los nuevos horarios
        }
    }

    fun deleteSchedule(schedule: Schedule, context: Context) {
        viewModelScope.launch {
            repository.delete(schedule)
            // TODO: Cancelar la alarma si el usuario elimina la clase
        }
    }
}

class ScheduleViewModelFactory(
    private val repository: ScheduleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}