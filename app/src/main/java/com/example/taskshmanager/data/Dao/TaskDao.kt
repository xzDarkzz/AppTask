package com.example.taskshmanager.data.Dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskshmanager.data.Entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    //Seleccionar todas las tareas
    @Query("SELECT * FROM task_table")
    fun getAllTask(): Flow<List<TaskEntity>>

}