package com.example.taskshmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

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
        dayOfTheMonth: String,
        month: String,
        year: String,
        hour: String,
        minute: String
    ) {
        viewModelScope.launch{
            val newTask = Task(
                title = title,
                description = description,
                day = day,
                dayOfTheMonth = dayOfTheMonth,
                month = month,
                year = year,
                hour = hour,
                minute = minute
            )
            repository.insertTask(newTask)
        }
    }

    fun editTask(
        id: Int,
        title: String,
        description: String,
        day: String,
        dayOfTheMonth: String,
        month: String,
        year: String,
        hour: String,
        minute: String
    ) {
        viewModelScope.launch {
            val updatedTask = Task(
                id = id,
                title = title,
                description = description,
                day = day,
                dayOfTheMonth = dayOfTheMonth,
                month = month,
                year = year,
                hour = hour,
                minute = minute
            )
            repository.update(updatedTask)
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