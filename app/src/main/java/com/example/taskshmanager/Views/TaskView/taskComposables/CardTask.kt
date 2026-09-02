package com.example.taskshmanager.Views.TaskView.taskComposables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskshmanager.data.Domain.Task
import java.time.LocalDate
import java.time.LocalDateTime

//These are the cards, they contend information of the user´s task
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsForTasks(
    modifier: Modifier,
    task: Task,
    EditTask: (time: TimePickerState,date: LocalDate, timeEnd: TimePickerState,dateEnd: LocalDate, task: Task) -> Unit,
    DoneTask: (task: Task) -> Unit,
    dateTime : LocalDateTime,
    finishDateTime: LocalDateTime


) {
    val accentColor = MaterialTheme.colorScheme.primary
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // 💡 La sombra define la tarjeta, no una línea
        modifier = modifier
            .fillMaxWidth()
            .height(115.dp)
    ) {
        // 2. Un Row principal que contendrá la barra de color a la izquierda y el contenido a la derecha
        Row(
            modifier = Modifier
                .fillMaxSize()
                // 💡 Este truco obliga a la barra morada a redondearse en las esquinas izquierdas
                .clip(RoundedCornerShape(12.dp))
        ) {

            // 3. LA BARRA DE ACENTO LATERAL
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp) // Grosor de la barra bonita
                    .background(accentColor)
            )

            // 4. Contenedor del contenido (lo que ya tenías, ahora con paddings ajustados)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f), // weight(1f) evita que el texto empuje los botones
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Título con tipografía más marcada
                    Text(
                        text = task.title,
                        fontSize = 20.sp, // 20sp mantiene fuerza sin romper el espacio
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Descripción con un tono más suave para generar jerarquía
                    Text(
                        text = task.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Fecha de la tarea
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Calendar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${String.format("%02d", finishDateTime.dayOfMonth)}/${String.format("%02d",finishDateTime.month.value)}/${finishDateTime.year}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Hora de la tarea (¡Ya con el formato quyeare arreglamos antes!)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Alarm,
                                contentDescription = "Hour",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${
                                    String.format(
                                        "%02d",
                                        dateTime.hour
                                    )
                                }:${String.format("%02d", dateTime.minute)} - ${String.format("%02d", finishDateTime.hour)}:${String.format("%02d",finishDateTime.minute)}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Columna de botones (idéntica a tu lógica)
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    IconButton(onClick = {
                        val timeStart = TimePickerState(
                            initialHour = dateTime.hour.toInt(),
                            initialMinute = dateTime.minute.toInt(),
                            is24Hour = true
                        )
                        val dateStart = java.time.LocalDate.of(
                            dateTime.year.toInt(),            // Año de la tarea actual
                            dateTime.month,           // Mes de la tarea actual
                            dateTime.dayOfMonth  // Día de la tarea actual
                        )
                        val timeEnd = TimePickerState(
                            initialHour = finishDateTime.hour.toInt(),
                            initialMinute = finishDateTime.minute.toInt(),
                            is24Hour = true
                        )
                        val dateEnd = java.time.LocalDate.of(
                            finishDateTime.year.toInt(),            // Año de la tarea actual
                            finishDateTime.month,           // Mes de la tarea actual
                            finishDateTime.dayOfMonth  // Día de la tarea actual
                        )
                        EditTask(timeStart,dateStart, timeEnd,dateEnd,task)
                    }, Modifier.size(28.dp)) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit this activity",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = {
                        DoneTask(task)
                    }, Modifier.size(28.dp)) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Complete this activity",
                            modifier = Modifier.fillMaxSize(),
                            tint = accentColor // El check ahora hace juego con la barra lateral
                        )
                    }
                }
            }
        }
    }
}