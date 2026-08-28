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
        val taskStarted = intent.getBooleanExtra("TASK_STARTED",false)
        if (taskId == -1) return

        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDataBase.getDatabase(context)
            val taskDao = database.taskDao()
            val repository = TaskRepository(taskDao)
            val task = repository.getTaskById(taskId) ?: return@launch


            val ahora = LocalDateTime.now()
            val fechaDeInicio = Instant.ofEpochMilli(task.dueDateEndMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val fechaEntrega = Instant.ofEpochMilli(task.dueDateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            //Calcula cuantas horas faltan para la tarea
            val duracion = Duration.between(ahora, fechaEntrega)
            val duracionInicio = Duration.between(ahora, fechaDeInicio)
            //Para el inicio
            val minutosRestantes = duracionInicio.toMinutes()
            val horasRestantes = duracionInicio.toHours()
            //Para el final
            val minutosFinales = duracion.toMinutes()
            val horasFinales = duracion.toHours()
            Log.d("HorasRestantes", "${duracion.toMinutes()}")

            // 1. Construcción del mensaje según el tiempo restante
            val (titulo, mensaje) =
                if(taskStarted){
                    when {
                        minutosRestantes <= 0 -> {
                            Pair("ES HORA INICA", "La tarea '${task.title}' la tienes que empezar ya.")
                        }
                        minutosRestantes <= 5 -> {
                            Pair("Estas a nada de iniciar", "${task.title.uppercase()} Esta apunto de iniciar, tienes:  5 minutos restantes")
                        }
                        minutosRestantes <= 10 -> {
                            Pair("¿Ya estas listo?", "La tarea : ${task.title} terminara en: 10 minutos(m)")
                        }
                        minutosRestantes <= 30 -> {
                            Pair("Preparate", "La tarea : ${task.title} iniciara en: 30 minutos(m)")
                        }
                        else -> {
                            Pair("Todavia hay tiempo para ${task.title}", "Falta(n) 1 hora(s) para ser iniciada.")
                        }
                    }
                }else{
                    when {
                        minutosFinales <= 0 -> {
                            Pair("La tarea ${task.title} ha vencido", "La tarea '${task.title}' ha llegado a su fecha límite.")
                        }
                        minutosFinales <= 2 -> {
                            Pair("YA FINALIZA", "La tarea '${task.title}' terminara en 2 minutos(m).")
                        }
                        minutosFinales <= 5 -> {
                            Pair("Recordatorio Urgente YA NO HAY TIEMPO", "${task.title.uppercase()} Esta apunto de finalizar, tienes:  5 minutos restantes")
                        }
                        minutosFinales <= 10 -> {
                            Pair("QUEDA POCO TIEMPO", "La tarea : ${task.title} terminara en: 10 minutos(m)")
                        }
                        minutosFinales<= 30 -> {
                            Pair("Rapido todavia hay tiempo", "La tarea : ${task.title} terminara en: 30 minutos(m)")
                        }
                        minutosFinales <= 60 -> {
                            Pair("Estas todavia a tiempo", "La tarea : ${task.title} terminara en: 60 minutos(m)")
                        }
                        horasFinales <= 24 -> {
                            Pair("Tranquilo, estas a tiempo: ${task.title}", "Le falta $horasFinales hora(s) para ser entregada.")
                        }
                        else -> {
                            val diasRestantes = duracion.toDays()
                            Pair("Todavia hay tiempo para ${task.title}", "Falta(n) $diasRestantes día(s) para la entrega.")
                        }
                    }
                }

            // 2. Disparar notificación visual y notificar al usuario
            mostrarNotificacion(context, taskId, titulo, mensaje)

            // 3. Re-programar el SIGUIENTE levantamiento en cadena (si aún no vence)
            val ahoraMillis = System.currentTimeMillis()
            //PARA INICIAR LA TAREA
            if (ahoraMillis < task.dueDateMillis) {
                val proximoLevantamientoMillis =
                    com.example.taskshmanager.Views.TaskView.Managers.NotificationManager(task.dueDateMillis).GetNextStartNotification()

                // Ajustamos para no pasarnos del tiempo límite de la tarea
                val finalTrigger = proximoLevantamientoMillis.coerceAtMost(task.dueDateMillis)

                AlarmScheduler.scheduleTaskAlarm(context, task.id, finalTrigger)
            }
            //PARA FINALIZAR LA TAREA
            else if(ahoraMillis < task.dueDateEndMillis){
                val proximoLevantamientoMillis =
                    com.example.taskshmanager.Views.TaskView.Managers.NotificationManager(task.dueDateMillis).GetNextFinishNotification()

                // Ajustamos para no pasarnos del tiempo límite de la tarea
                val finalTrigger = proximoLevantamientoMillis.coerceAtMost(task.dueDateMillis)

                AlarmScheduler.scheduleTaskAlarm(context, task.id, finalTrigger, true)
            }
        }
    }

    private fun mostrarNotificacion(context: Context, notificationId: Int, titulo: String, mensaje: String) {
        val channelId = "remind_planner_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorios de Tareas",NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_popup_reminder) // Cambiar por el ícono de tu app
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}