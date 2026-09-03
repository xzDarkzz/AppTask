package com.example.taskshmanager.data

import com.example.taskshmanager.data.Dao.ScheduleDao
import com.example.taskshmanager.data.Domain.Schedule
import com.example.taskshmanager.data.Entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleRepository(
    private val scheduleDao: ScheduleDao
) {
    fun getAllSchedules(): Flow<List<Schedule>> {
        return scheduleDao.getAllSchedules().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getScheduleById(id: Int): Schedule? {
        return scheduleDao.getScheduleById(id)?.toDomain()
    }

    suspend fun insertSchedule(schedule: Schedule): Long {
        return scheduleDao.insert(schedule.toEntity())
    }

    suspend fun update(schedule: Schedule) {
        scheduleDao.update(schedule.toEntity())
    }

    suspend fun delete(schedule: Schedule) {
        scheduleDao.delete(schedule.toEntity())
    }
}

// Mappers para convertir entre Entity y Domain
fun ScheduleEntity.toDomain() = Schedule(id, subject, description, day, startTimeMillis, endTimeMillis)
fun Schedule.toEntity() =
    ScheduleEntity(id, subject, description, day, startTimeMillis, endTimeMillis)