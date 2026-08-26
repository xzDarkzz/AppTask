package com.example.taskshmanager.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskshmanager.Views.TaskView.AlarmScheduler
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    fun agendarNotificacion(dueDateMillis: Long): Long {
        val ahora = System.currentTimeMillis()
        val dosDiaEnMillis = 48 * 60 * 60 * 1000L
        val unDiaEnMillis = 24 * 60 * 60 * 1000L
        val doceHorasEnMilis = 12*60 * 60 * 1000L
        val unaHoraEnMillis = 60 * 60 * 1000L
        val treintaMinutosEnMillis = 30*60*1000L
        val diezMinutosEnMilis = 10*60*1000L
        val cincoMinutosEnMilis = 5*60*1000L
        val dosMinutoEnMilis = 2*60*1000L
        val tiempoRestante = dueDateMillis - ahora
        val nextAlarm = when{
            tiempoRestante <= dosMinutoEnMilis -> {
                dueDateMillis
            }
            tiempoRestante <= cincoMinutosEnMilis -> {
                dueDateMillis - dosMinutoEnMilis
            }
            tiempoRestante <= diezMinutosEnMilis -> {
                dueDateMillis - cincoMinutosEnMilis
            }
            tiempoRestante <= treintaMinutosEnMillis ->{
                dueDateMillis - diezMinutosEnMilis
            }
            tiempoRestante <= unaHoraEnMillis -> {
                dueDateMillis - treintaMinutosEnMillis
            }
            tiempoRestante <= doceHorasEnMilis -> {
                dueDateMillis - unaHoraEnMillis
            }
            tiempoRestante <= unDiaEnMillis -> {
                dueDateMillis - doceHorasEnMilis
            }
            tiempoRestante <= dosDiaEnMillis -> {
                dueDateMillis - unDiaEnMillis
            }

            else -> {
                ahora + unDiaEnMillis
            }
        }
        Log.d("Entrando", "entro en el agendar${nextAlarm}")
        return nextAlarm
    }
    val tasksState: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTask(
        title: String,
        description: String,
        day: String,
        dueDateMillis : Long,
        context: Context
    ){
        viewModelScope.launch{
            val newTask = Task(
                title = title,
                description = description,
                day = day,
                dueDateMillis = dueDateMillis
            )
            val id = repository.insertTask(newTask).toInt()
            val nextAlarm = agendarNotificacion(dueDateMillis)
            Log.d("Entrando", "Ya la mando ${nextAlarm}")

            AlarmScheduler.scheduleTaskAlarm(context, id, nextAlarm)
        }

    }

    fun editTask(
        id: Int,
        title: String,
        description: String,
        day: String,
        dueDateMillis : Long,
        context : Context
    ) {
        viewModelScope.launch {
            val updatedTask = Task(
                id = id,
                title = title,
                description = description,
                day = day,
                dueDateMillis = dueDateMillis
            )
            repository.update(updatedTask)
            val nextAlarm = agendarNotificacion(dueDateMillis)
            Log.d("Entrando", "Ya la mando padrino ${nextAlarm}")
            AlarmScheduler.scheduleTaskAlarm(context, id, nextAlarm)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch{
            repository.delete(task)
        }
    }
}

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 1. Verificamos que la clase solicitada sea nuestro TaskViewModel
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // 2. Le pasamos el repository por el constructor y lo devolvemos
            return TaskViewModel(repository) as T
        }
        // 3. Si no es TaskViewModel, lanzamos un error claro
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}