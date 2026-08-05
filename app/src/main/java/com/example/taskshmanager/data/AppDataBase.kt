package com.example.taskshmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskshmanager.data.Dao.TaskDao
import com.example.taskshmanager.data.Entity.TaskEntity


@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase?= null
        fun getDatabase(context: Context): AppDataBase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "task_table"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}

