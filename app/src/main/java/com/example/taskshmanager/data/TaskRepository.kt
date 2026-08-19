package com.example.taskshmanager.data

import com.example.taskshmanager.data.Dao.TaskDao
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.data.Entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val taskDao: TaskDao
) {

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTask().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun insertTask(task: Task) {
        taskDao.insert(task.toEntity())
    }

    suspend fun update(task: Task) {
        taskDao.update(task.toEntity())
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task.toEntity())
    }
}

// Mappers
fun TaskEntity.toDomain() = Task(id, title, description, day, dueDateMillis)

fun Task.toEntity() = TaskEntity(id, title, description, day, dueDateMillis)