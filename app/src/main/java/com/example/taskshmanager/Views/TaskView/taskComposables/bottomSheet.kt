package com.example.taskshmanager.Views.TaskView.taskComposables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskshmanager.Views.TaskView.Managers.TaskManagerInformation
import com.example.taskshmanager.Views.TimeManager
import com.example.taskshmanager.Views.composables.TimeSelector

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTasks(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    DismissRequest: () -> Unit,
    OpenTimeSelector: (option: Int, optionTime: Int) -> Unit,
    timeManager: TimeManager,
    DoneRequest: () -> Unit,
    taskManager: TaskManagerInformation,
    timeManagerFinish: TimeManager
) {
    // Variables
    val titleState = rememberTextFieldState(initialText = taskManager.title)
    val descriptionState = rememberTextFieldState(initialText = taskManager.description)

    // ------------------------------------- //
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { DismissRequest() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.70f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    Text(
                        text = if (taskManager.id == 0) "Nueva Tarea" else "Editar Tarea",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // This is for the title
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = titleState,
                        label = { Text("Título de la tarea") },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(20),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // This is for the description
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = descriptionState,
                        label = { Text("Descripción") },
                        lineLimits = TextFieldLineLimits.MultiLine(2,3),
                        inputTransformation = InputTransformation.maxLength(100),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Card estilizada para la selección de tiempo (Inicio y Fin)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Time selector for the start time
                            Column {
                                Text(
                                    text = "Fecha y hora de inicio",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                TimeSelector(
                                    modifier = Modifier.fillMaxWidth(),
                                    OpenTimeSelector = { option, optionTime ->
                                        OpenTimeSelector(option, optionTime)
                                    },
                                    timeManager = timeManager,
                                    timeManagerFinish = timeManagerFinish,
                                    optionTime = 0
                                )
                            }

                            // Time selector for the end time
                            Column {
                                Text(
                                    text = "Fecha y hora de entrega",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                TimeSelector(
                                    modifier = Modifier.fillMaxWidth(),
                                    OpenTimeSelector = { option, optionTime ->
                                        OpenTimeSelector(option, optionTime)
                                    },
                                    timeManager = timeManager,
                                    timeManagerFinish = timeManagerFinish,
                                    optionTime = 1
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.End)
                ) {
                    OutlinedButton(
                        onClick = { DismissRequest() },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            val title = titleState.text.toString()
                            val description = descriptionState.text.toString()
                            val id = taskManager.id
                            taskManager.UpdateInfo(title, description, id)
                            DoneRequest()
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}