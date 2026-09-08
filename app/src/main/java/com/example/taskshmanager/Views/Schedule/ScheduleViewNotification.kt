package com.example.taskshmanager.Views.Schedule

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.taskshmanager.data.AppDataBase
import com.example.taskshmanager.data.Domain.Schedule
import com.example.taskshmanager.data.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class ScheduleViewNotification : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val scheduleId = intent.getIntExtra("SCHEDULE_ID", -1)
        if (scheduleId == -1) return

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDataBase.getDatabase(context)
            val repository = ScheduleRepository(database.ScheduleDao())
            val schedule = repository.getScheduleById(scheduleId) ?: return@launch
            val ahora = System.currentTimeMillis()
            val tiempoRestante = schedule.startTimeMillis - ahora

            val (title,description) = when{
                tiempoRestante <= schedule.startTimeMillis -> {
                    Pair("YA EMPEZO", "Tu clase: ${schedule.subject} acaba de empezar")
                }
                tiempoRestante <= 5*60*1000L -> {
                    Pair("Esta apunto de empezar", "Tu clase: ${schedule.subject} empieza en 5m")
                }else -> {
                    Pair("Ya casi empieza", "Tu clase: ${schedule.subject} empieza en 15m")
                }
            }


            // 1. Mostrar la notificación actual
            mostrarNotificacion(
                context,
                scheduleId,
                title,
                description
            )

            // 2. Calcular y programar la siguiente alarma usando tu lógica
            val manager = NotificationManagerSchedule(schedule.startTimeMillis, schedule.repetDays)
            var nextTrigger = manager.getNextStartAlarm()
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
    }

    private fun mostrarNotificacion(context: Context, notificationId: Int, titulo: String, mensaje: String) {
        val channelId = "schedule_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorios de Clases",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_popup_reminder)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}