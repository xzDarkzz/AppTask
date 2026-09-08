package com.example.taskshmanager.Views.TaskView.Managers

class NotificationManagerTask(val dueDateMillis : Long) {


    private val ahora = System.currentTimeMillis()
    private val dosDiaEnMillis = 48 * 60 * 60 * 1000L
    private val unDiaEnMillis = 24 * 60 * 60 * 1000L
    private val doceHorasEnMilis = 12*60 * 60 * 1000L
    private val unaHoraEnMillis = 60 * 60 * 1000L
    private val treintaMinutosEnMillis = 30*60*1000L
    private val diezMinutosEnMilis = 10*60*1000L
    private val cincoMinutosEnMilis = 5*60*1000L
    private val dosMinutoEnMilis = 2*60*1000L
    private val tiempoRestante = dueDateMillis - ahora

    fun GetNextFinishNotification() : Long{
        val nextAlarm = when{
            tiempoRestante <= dosMinutoEnMilis -> {
                dueDateMillis
            }
            tiempoRestante <= cincoMinutosEnMilis -> {
                dueDateMillis - dosMinutoEnMilis
            }
            tiempoRestante <= diezMinutosEnMilis -> {
                dueDateMillis - cincoMinutosEnMilis
            }
            tiempoRestante <= treintaMinutosEnMillis ->{
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
        return nextAlarm
    }
    fun GetNextStartNotification() : Long {
        val nextAlarm = when{
            tiempoRestante <= cincoMinutosEnMilis -> {
                dueDateMillis
            }
            tiempoRestante <= diezMinutosEnMilis -> {
                dueDateMillis - cincoMinutosEnMilis
            }
            tiempoRestante <= treintaMinutosEnMillis ->{
                dueDateMillis - diezMinutosEnMilis
            }
            tiempoRestante <= unaHoraEnMillis -> {
                dueDateMillis - treintaMinutosEnMillis
            }

            else -> {
               dueDateMillis - unaHoraEnMillis
            }

        }
        return nextAlarm
    }

}