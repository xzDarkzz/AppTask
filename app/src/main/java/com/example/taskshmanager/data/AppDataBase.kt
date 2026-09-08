package com.example.taskshmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskshmanager.data.Dao.ScheduleDao
import com.example.taskshmanager.data.Dao.TaskDao
import com.example.taskshmanager.data.Entity.ScheduleEntity
import com.example.taskshmanager.data.Entity.TaskEntity


@Database(
    entities = [TaskEntity::class, ScheduleEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun ScheduleDao(): ScheduleDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase?= null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE task_table ADD COLUMN dueDateEndMillis INTEGER NOT NULL DEFAULT 0")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `schedule_table` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `subject` TEXT, 
                        `description` TEXT, 
                        `day` TEXT, 
                        `startTimeMillis` INTEGER, 
                        `endTimeMillis` INTEGER,
                        `repetDays` TEXT
                    )
                """.trimIndent())
            }
        }
        fun getDatabase(context: Context): AppDataBase{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "task_table"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

