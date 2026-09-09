package com.example.taskshmanager.Views.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskshmanager.Views.TimeManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    OpenTimeSelector: (option: Int, optionTimeDate: Int) -> Unit,
    timeManager: TimeManager,
    timeManagerFinish: TimeManager,
    optionTime: Int = 0
) {
    val activeTimeManager = if (optionTime == 1) timeManagerFinish else timeManager

    val formattedDate = "${String.format("%02d", activeTimeManager.dayOfMonth)}/${String.format("%02d", activeTimeManager.month)}"
    val formattedTime = "${String.format("%02d", activeTimeManager.hour)}:${String.format("%02d", activeTimeManager.minute)}"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selector de Fecha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Icono Fecha",
                    tint = MaterialTheme.colorScheme.primary
                )
                TextButton(
                    onClick = { OpenTimeSelector(2, optionTime) },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = formattedDate,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .padding(horizontal = 4.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Selector de Hora
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    imageVector = Icons.Default.Alarm,
                    contentDescription = "Icono Hora",
                    tint = MaterialTheme.colorScheme.primary
                )
                TextButton(
                    onClick = { OpenTimeSelector(1, optionTime) },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = formattedTime,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}