package com.example.taskshmanager.Views.TaskView.alarmFiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taskshmanager.Views.TaskView.Managers.NotificationManager
import com.example.taskshmanager.data.AppDataBase
import com.example.taskshmanager.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Clase para identificar actualizaciones o reinicios y reagendar las notificaciones.
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Verificamos si la señal es de Reinicio de Teléfono o de Actualización de App
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            // 1. Pedimos tiempo extra al sistema para no ser destruidos prematuramente
            val pendingResult = goAsync()
            Log.d("Entrando", "entro")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val database = AppDataBase.getDatabase(context)
                    val taskDao = database.taskDao()
                    val repository = TaskRepository(taskDao)

                    // 2. Usamos .first() para tomar la lista actual UNA SOLA VEZ y cerrar el flujo
                    val entities = repository.getAllTasks().first()

                    val ahora = System.currentTimeMillis()
                    entities.forEach { taskEntity ->
                        // SI LA TAREA AUN NO SE AH EMPEZADO
                        if (taskEntity.dueDateMillis > ahora) {

                            val nextAlarm = NotificationManager(taskEntity.dueDateMillis).GetNextStartNotification()

                            // Re-agendamos en el AlarmManager
                            AlarmScheduler.scheduleTaskAlarm(context, taskEntity.id, nextAlarm)
                        }
                        //REASIGNACION SI LA TAREA YA SE EMPEZO
                        else{
                            val nextAlarm = NotificationManager(taskEntity.dueDateEndMillis).GetNextFinishNotification()

                            // Re-agendamos en el AlarmManager
                            AlarmScheduler.scheduleTaskAlarm(context, taskEntity.id, nextAlarm, true)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    // 3. Notificamos a Android que terminamos para que libere el Receiver
                    pendingResult.finish()
                }
            }
        }
    }
}