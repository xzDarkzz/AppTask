package com.example.taskshmanager.Views.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.TimePickerDisplayMode
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskshmanager.Views.ComposableTimeManager
import com.example.taskshmanager.Views.TimeManager

//HourSelector


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourSelector(
    modifier: Modifier,
    onDismissRequest : () -> Unit,
    dismissButton : () -> Unit,
    timeManager: TimeManager,
    composableTimeManager: ComposableTimeManager,
){
    val state = rememberTimePickerState()
    fun confirmButton(){
        val selectedHour = state
        timeManager.ChangeHour(selectedHour)
    }

    TimePickerDialog(
        title = { TimePickerDialogDefaults.Title(displayMode = TimePickerDisplayMode.Picker) },
        onDismissRequest = {onDismissRequest()},
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButton()
                    composableTimeManager.closeBottomSheet()
                },
            ) {
                Text("Ok")
            }
        },
        dismissButton = { TextButton(onClick = {dismissButton()}) { Text("Cancel") } },
        modeToggleButton = {},
    ) {
        TimePicker(state = state)
    }
}

//DateSelector
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    onDismissRequest: () -> Unit,
    dismissButton: () -> Unit,
    timeManager: TimeManager,
    composableTimeManager: ComposableTimeManager
){

    val datePickerState = rememberDatePickerState()
    fun confirmButton(){
        val selectedDate = datePickerState.getSelectedDate()
        if (selectedDate?.monthValue !=null || selectedDate?.dayOfMonth != null){
            timeManager.ChangeDate(selectedDate)
            composableTimeManager.closeBottomSheet()
        }
    }
    DatePickerDialog(
        onDismissRequest = {onDismissRequest()},
        confirmButton = {
            TextButton(onClick = {
                confirmButton()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dismissButton()
            }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}