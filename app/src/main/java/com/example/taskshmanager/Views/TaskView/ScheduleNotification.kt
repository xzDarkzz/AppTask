package com.example.taskshmanager.Views.TaskView

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ScheduleNotification() : BroadcastReceiver() {
    companion object{
        const val NOTIFICATION_ID = 0
    }
    override fun onReceive(context: Context, intent: Intent) {
        crearNotificacion(context)
    }

    private fun crearNotificacion(context: Context) {
        val notification = NotificationCompat.Builder(context,"TaskManager")
            .setContentTitle("Tu tarea va a finalizar")
            .setContentText("Hola hjejejej")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Saludoooos")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID,notification)
    }

}