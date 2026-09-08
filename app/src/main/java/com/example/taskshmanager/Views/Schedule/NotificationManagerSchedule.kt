package com.example.taskshmanager.Views.Schedule

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class NotificationManagerSchedule(
    val dueDateStartMillis: Long,
    val nextDayValueSelected: String
) {

    private val ahora = System.currentTimeMillis()
    val now = LocalDateTime.now()
    val valueOfTheDay = Instant.ofEpochMilli(dueDateStartMillis).atZone(ZoneId.systemDefault()).toLocalDateTime().dayOfWeek.value
    private val tiempoRestante = dueDateStartMillis - ahora
    fun getNextStartAlarm() : Long{
        var nextAlarm: Long = 0
        if(tiempoRestante <= 0){
            val values = getSeparateValues(nextDayValueSelected)
            val lastDay = values[values.size -1]
            var nextValue= 0
            if(lastDay == valueOfTheDay){
                nextValue = values[0]
                val result = valueOfTheDay - nextValue
                nextAlarm = ((dueDateStartMillis - result*24*60*60*1000L) + 7*24*60*60*1000L)

            }else{
                if(values.contains(valueOfTheDay)){
                    val findIndex = values.indexOf(valueOfTheDay)
                    nextValue = values[findIndex+1]
                    val result = nextValue - valueOfTheDay
                    nextAlarm = dueDateStartMillis + result*24*60*60*1000L
                }
                else{
                    var valuesOrder = values.map {it -valueOfTheDay}.sorted().filter { it >= 0 }
                    nextAlarm = dueDateStartMillis + valuesOrder[0]*24*60*60*1000L

                }
            }
        }else{
            val quinceMinutosEnMilis = 10*60*1000L
            val cincoMinutosEnMilis = 5*60*1000L
            nextAlarm = when{
                tiempoRestante <= cincoMinutosEnMilis -> {
                    dueDateStartMillis
                }
                tiempoRestante <=  quinceMinutosEnMilis -> {
                    dueDateStartMillis - cincoMinutosEnMilis
                }
                else -> {
                    dueDateStartMillis - quinceMinutosEnMilis
                }
            }

        }
        return nextAlarm
    }
    fun getSeparateValues (value: String) : List<Int>{
        val classDays = value.map { it.toString().toInt() }.sorted()
        return classDays

    }


}