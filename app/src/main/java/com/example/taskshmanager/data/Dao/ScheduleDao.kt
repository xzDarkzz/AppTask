package com.example.taskshmanager.data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskshmanager.data.Entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insert(schedule: ScheduleEntity): Long

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Delete
    suspend fun delete(schedule: ScheduleEntity)

    // Seleccionar todas las clases ordenadas por la hora de inicio
    @Query("SELECT * FROM schedule_table ORDER BY startTimeMillis ASC")
    fun getAllSchedules(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedule_table WHERE id = :scheduleId LIMIT 1")
    suspend fun getScheduleById(scheduleId: Int): ScheduleEntity?
}