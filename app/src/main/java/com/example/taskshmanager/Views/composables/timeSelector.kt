package com.example.taskshmanager.Views.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskshmanager.Views.TimeManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelector(
    modifier: Modifier,
    OpenTimeSelector: (option: Int, optionTimeDate:Int) -> Unit,
    timeManager: TimeManager,
    timeManagerFinish: TimeManager,
    optionTime:Int = 0
){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(58.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "CalendarIcon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = {OpenTimeSelector(2, optionTime)}, modifier = Modifier) {
                Text(
                    if(optionTime == 1){
                        "${String.format("%02d", timeManagerFinish.dayOfMonth)}/${
                            String.format(
                                "%02d",
                                timeManagerFinish.month
                            )
                        }"
                    } else {
                        "${String.format("%02d", timeManager.dayOfMonth)}/${
                            String.format(
                                "%02d",
                                timeManager.month
                            )
                        }"
                    },
                    fontSize = 35.sp
                )
            }
        }
        VerticalDivider(thickness = 2.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.Alarm,
                contentDescription = "AlarmIcon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = {OpenTimeSelector(1, optionTime)}) {
                Text(if (optionTime == 1){
                    "${String.format("%02d", timeManagerFinish.hour)}:${
                        String.format(
                            "%02d",
                            timeManagerFinish.minute
                        )
                    }"
                } else{
                    "${String.format("%02d", timeManager.hour)}:${
                        String.format(
                            "%02d",
                            timeManager.minute
                        )
                    }"
                },
                    fontSize = 35.sp
                )
            }
        }
    }

}