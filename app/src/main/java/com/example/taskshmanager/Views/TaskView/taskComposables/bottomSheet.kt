package com.example.taskshmanager.Views.TaskView.taskComposables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskshmanager.Views.TaskView.Managers.TaskManagerInformation
import com.example.taskshmanager.Views.TimeManager
import com.example.taskshmanager.Views.composables.TimeSelector

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTasks(
    modifier: Modifier,
    sheetState: SheetState,
    DismissRequest: () -> Unit,
    OpenTimeSelector: (option: Int, optionTime : Int) -> Unit,
    timeManager: TimeManager,
    DoneRequest : () -> Unit,
    taskManager: TaskManagerInformation,
    timeManagerFinish: TimeManager
){
    //Variables

    val titleState = rememberTextFieldState(initialText = taskManager.title)
    val descriptionState = rememberTextFieldState(initialText = taskManager.description)



    // ------------------------------------- //
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { DismissRequest() }
    ) {
        Box(modifier= Modifier
            .height(450.dp)
            .fillMaxWidth()
            .padding(15.dp)){
            Column(modifier = Modifier) {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    //This is for the title
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = titleState,
                        label = { Text("TITLE") },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(20)


                    )
                    //This is for the description
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = descriptionState,
                        label = { Text("Description") },
                        lineLimits = TextFieldLineLimits.MultiLine(2),
                        inputTransformation = InputTransformation.maxLength(100)
                    )

                    Column() {
                        Text("Start")
                        //Time selector for the start time
                        TimeSelector(
                            modifier = Modifier,
                            OpenTimeSelector = {
                                    option, optionTime ->
                                OpenTimeSelector(option, optionTime)
                            },
                            timeManager = timeManager,
                            timeManagerFinish

                        )
                        //Time selector for the end time
                        Text("End")
                        TimeSelector(
                            modifier = Modifier,
                            OpenTimeSelector = {
                                    option, optionTime ->
                                OpenTimeSelector(option, optionTime)

                            },
                            timeManager = timeManager,
                            timeManagerFinish,
                            optionTime = 1
                        )
                    }


                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        alignment = Alignment.End
                    ),
                ) {
                    Button(onClick = {}) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        var title = titleState.text.toString()
                        var description = descriptionState.text.toString()
                        var id = taskManager.id
                        taskManager.UpdateInfo(title,description, id)
                        DoneRequest()

                    }
                    ) {
                        Text("Done")
                    }
                }

            }
        }
    }
}

