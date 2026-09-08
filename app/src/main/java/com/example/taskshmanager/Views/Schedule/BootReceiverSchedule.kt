package com.example.taskshmanager.Views.Schedule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskshmanager.data.AppDataBase
import com.example.taskshmanager.data.Domain.Schedule
import com.example.taskshmanager.data.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class BootReceiverSchedule : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {

            val pendingResult = goAsync()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val database = AppDataBase.getDatabase(context)
                    val repository = ScheduleRepository(database.ScheduleDao())
                    val schedules = repository.getAllSchedules().first()
                    val ahora = System.currentTimeMillis()

                    schedules.forEach { schedule ->
                        // Re-agendar todas las clases usando tu lógica
                        val manager = NotificationManagerSchedule(schedule.startTimeMillis, schedule.repetDays)
                        var nextTrigger = manager.getNextStartAlarm()
                        val tiempoRestante = schedule.startTimeMillis - ahora
                        if (nextTrigger > 0L) {
                            if(tiempoRestante <= 0){
                                val dayOfWeeK = Instant.ofEpochMilli(nextTrigger).atZone(ZoneId.systemDefault()).toLocalDateTime().dayOfWeek.toString()
                                //Se actualiza la base de datos
                                val scheduleNew = Schedule(schedule.id,schedule.subject,schedule.description,dayOfWeeK,nextTrigger,schedule.endTimeMillis, schedule.repetDays)
                                nextTrigger = nextTrigger - 15*60*1000L
                                AlarmManagerSchedule.scheduleClassAlarm(context, schedule.id, nextTrigger)


                            }else{
                                AlarmManagerSchedule.scheduleClassAlarm(context, schedule.id, nextTrigger)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}