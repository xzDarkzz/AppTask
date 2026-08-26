package com.example.taskshmanager.Views.TaskView

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
                        // Si la tarea aún no ha vencido, reprogramamos su alarma
                        if (taskEntity.dueDateMillis > ahora) {

                            val dueDateMillis = taskEntity.dueDateMillis
                            val dosDiaEnMillis = 48 * 60 * 60 * 1000L
                            val unDiaEnMillis = 24 * 60 * 60 * 1000L
                            val doceHorasEnMilis = 12 * 60 * 60 * 1000L
                            val unaHoraEnMillis = 60 * 60 * 1000L
                            val treintaMinutosEnMillis = 30 * 60 * 1000L
                            val diezMinutosEnMilis = 10 * 60 * 1000L
                            val cincoMinutosEnMilis = 5 * 60 * 1000L
                            val dosMinutosEnMilis = 2 * 60 * 1000L
                            val tiempoRestante = dueDateMillis - ahora

                            val nextAlarm = when {
                                tiempoRestante <= dosMinutosEnMilis -> {
                                    dueDateMillis
                                }
                                tiempoRestante <= cincoMinutosEnMilis -> {
                                    dueDateMillis - dosMinutosEnMilis
                                }
                                tiempoRestante <= diezMinutosEnMilis -> {
                                    dueDateMillis - cincoMinutosEnMilis
                                }
                                tiempoRestante <= treintaMinutosEnMillis -> {
                                    dueDateMillis - diezMinutosEnMilis
                                }
                                tiempoRestante <= unaHoraEnMillis -> {
                                    dueDateMillis - treintaMinutosEnMillis
                                }
                                tiempoRestante <= doceHorasEnMilis -> {
                                    dueDateMillis - unaHoraEnMillis
                                }
                                tiempoRestante <= unDiaEnMillis -> {
                                    dueDateMillis - doceHorasEnMilis
                                }
                                tiempoRestante <= dosDiaEnMillis -> {
                                    dueDateMillis - unDiaEnMillis
                                }
                                else -> {
                                    ahora + unDiaEnMillis
                                }
                            }
                            Log.d("Entrando", "entro ${nextAlarm}")

                            // Re-agendamos en el AlarmManager
                            AlarmScheduler.scheduleTaskAlarm(context, taskEntity.id, nextAlarm)
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