package com.example.taskshmanager.Views.TaskView

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.taskshmanager.data.AppDataBase
import com.example.taskshmanager.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class ScheduleNotification : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("TASK_ID", -1)
        if (taskId == -1) return

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDataBase.getDatabase(context)
            val taskDao = database.taskDao()
            val repository = TaskRepository(taskDao)
            val task = repository.getTaskById(taskId) ?: return@launch


            val ahora = LocalDateTime.now()
            val fechaEntrega = Instant.ofEpochMilli(task.dueDateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            //Calcula cuantas horas faltan para la tarea
            val duracion = Duration.between(ahora, fechaEntrega)


            //Las convierte en horas
            val minutosRestantes = duracion.toMinutes()
            val horasRestantes = duracion.toHours()
            Log.d("HorasRestantes", "${duracion.toMinutes()}")

            // 1. Construcción del mensaje según el tiempo restante
            val (titulo, mensaje) = when {
                minutosRestantes <= 0 -> {
                    Pair("La tarea ${task.title} ha vencido", "La tarea '${task.title}' ha llegado a su fecha límite.")
                }
                minutosRestantes < 5 -> {
                    Pair("Recordatorio Urgente YA NO HAY TIEMPO", "${task.title.uppercase()} Esta apunto de finalizar, tienes:  ${minutosRestantes} minutos restantes")
                }
                minutosRestantes < 30 -> {
                    Pair("Rapido todavia hay tiempo", "La tarea : ${task.title} terminara en: ${minutosRestantes} minutos(m)")
                }
                minutosRestantes < 60 -> {
                    val minutosRestantes = duracion.toMinutes()
                    Pair("Estas todavia a tiempo", "La tarea : ${task.title} terminara en: ${minutosRestantes} minutos(m)")
                }
                horasRestantes < 24 -> {
                    Pair("Tranquilo, estas a tiempo: ${task.title}", "Le falta $horasRestantes hora(s) para ser entregada.")
                }
                else -> {
                    val diasRestantes = duracion.toDays()
                    Pair("Todavia hay tiempo", "Falta(n) $diasRestantes día(s) para la entrega.")
                }
            }

            // 2. Disparar notificación visual y notificar al usuario
            mostrarNotificacion(context, taskId, titulo, mensaje)

            // 3. Re-programar el SIGUIENTE levantamiento en cadena (si aún no vence)
            val ahoraMillis = System.currentTimeMillis()
            if (ahoraMillis < task.dueDateMillis) {
                val unDiaEnMillis = 24 * 60 * 60 * 1000L
                val unaHoraEnMillis = 60 * 60 * 1000L
                val treintaMinutosEnMillis = 30*60*1000L
                val cincoMinutosEnMilis = 5*60*1000L
                val unMinutoEnMilis = 60*1000L

                val proximoLevantamientoMillis = when {
                    minutosRestantes < 5 -> {ahoraMillis + unMinutoEnMilis}
                    minutosRestantes < 15 -> {ahoraMillis + cincoMinutosEnMilis}
                    minutosRestantes < 60 -> {ahoraMillis + treintaMinutosEnMillis}
                    horasRestantes < 24 -> {ahoraMillis + unaHoraEnMillis}
                    else -> {
                        ahoraMillis + unDiaEnMillis
                    }
                }

                // Ajustamos para no pasarnos del tiempo límite de la tarea
                val finalTrigger = proximoLevantamientoMillis.coerceAtMost(task.dueDateMillis)

                AlarmScheduler.scheduleTaskAlarm(context, task.id, finalTrigger)
            }
        }
    }

    private fun mostrarNotificacion(context: Context, notificationId: Int, titulo: String, mensaje: String) {
        val channelId = "remind_planner_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorios de Tareas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_popup_reminder) // Cambiar por el ícono de tu app
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}