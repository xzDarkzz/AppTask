package com.example.taskshmanager.Views.TaskView

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.TimePickerDisplayMode
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskshmanager.ComposableTimeManager
import com.example.taskshmanager.TimeManager
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.ui.TaskViewModel

//Select The Day Of the tasks
@Composable
fun SelectDay(modifier: Modifier, daySelected: String, onPreviewsDay : () -> Unit, onAfterDay : () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(150.dp)){
            Text(modifier = Modifier.align(Alignment.CenterStart), text = daySelected, fontSize = 25.sp, color = Color.LightGray)
        }
        //Buttoms to changes the Day
        Box(modifier = Modifier){
            Row(modifier = Modifier) {

                IconButton(onClick = {onPreviewsDay()}, modifier = Modifier) {
                    Icon(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Before Botton"
                    )
                }
                IconButton(onClick = {onAfterDay()}, modifier = Modifier) {
                    Icon(
                        Icons.Filled.ArrowForwardIos,
                        contentDescription = "After Botton"
                    )
                }
            }
        }
    }
}

//These are the cards, they contend information of the user´s task
@Composable
fun CardsForTasks(modifier: Modifier, title: String, description: String){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .width(320.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(title, fontSize = 25.sp)
                Text(description, modifier = Modifier)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                IconButton(onClick = {}, Modifier.size(27.dp)) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Delete this activity",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(onClick = {}, Modifier.size(27.dp)) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Delete this activity",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }


        }
    }
}

//HourSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourSelector(
    modifier: Modifier,
    onDismissRequest : () -> Unit,
    confirmButton : (hour: String) -> Unit,
    dismissButton : () -> Unit
){
    val state = rememberTimePickerState()
    TimePickerDialog(
        title = { TimePickerDialogDefaults.Title(displayMode = TimePickerDisplayMode.Picker) },
        onDismissRequest = {onDismissRequest()},
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButton("${state.hour} : ${state.minute}")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelector(
    modifier: Modifier,
    OpenTimeSelector: (option: Int) -> Unit,
    timeManager: TimeManager
){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(58.dp), horizontalArrangement = Arrangement.SpaceAround) {
        TextButton(onClick = {OpenTimeSelector(2)}, modifier = Modifier) {
            Text("${timeManager.month}/${timeManager.dayOfMonth}", fontSize = 35.sp)
        }
        VerticalDivider(thickness = 5.dp)
        TextButton(onClick = {OpenTimeSelector(1)}) {
            Text(timeManager.time, fontSize = 35.sp)
        }
    }

}




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTasks(
    modifier: Modifier,
    sheetState: SheetState,
    DismissRequest: () -> Unit,
    OpenTimeSelector: (option: Int) -> Unit,
    timeManager: TimeManager,
    DoneRequest : (title:String, description: String) -> Unit
){
    //Variables

    val titleState = rememberTextFieldState(initialText = "")
    val descriptionState = rememberTextFieldState(initialText = "")



    // ------------------------------------- //
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { DismissRequest() }
    ) {
        Box(modifier= Modifier
            .height(380.dp)
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

                    TimeSelector(
                        modifier = Modifier,
                        OpenTimeSelector = {
                                option -> var option = option
                            OpenTimeSelector(option)
                        },
                        timeManager = timeManager
                    )

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
                        DoneRequest(title,description)

                    }
                    ) {
                        Text("Done")
                    }
                }

            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(
    modifier: Modifier = Modifier,
    days: List<String>,
    viewModel: ComposableTimeManager = viewModel(),
    timeManager: TimeManager = viewModel(),
    viewModelTask: TaskViewModel? = null
){

    //Carga de todas las tareas
   val tasksState = viewModelTask?.tasksState?.collectAsState(initial = emptyList())
    val tasks = tasksState?.value
    val localTasks = remember { mutableStateListOf<Task>() }
    //BottomSheet to add new task
    var showBottomSheet by remember { mutableStateOf(false) }
    var optionDatePicker by remember { mutableIntStateOf(0)}
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    var indexSelection by remember {mutableIntStateOf(0)}
    var daySelected = days[indexSelection]
    //Limpieza y filtro de tareas
    if(tasks?.isNotEmpty() == true){

        localTasks.clear()
        var newList = tasks.filter { it.day.lowercase() == daySelected.lowercase() }
        localTasks.addAll(newList)

    }
    else{
        localTasks.clear()
    }



    //Seleccion de Dia de tareas
    fun SelectDayTask(option : Int){
        when(option){
            1 -> if (indexSelection < days.size -1){
                indexSelection ++
            } else{
                indexSelection = 0
            }
            2 -> if (indexSelection > 0){
                indexSelection --
            } else{
                indexSelection = days.size -1
            }
        }
        daySelected = days[indexSelection]
    }

    fun changeStateButtomSheet(){
        showBottomSheet = !showBottomSheet
    }

    //Add to the database the tasks

    fun DoneAddTasks(title: String, description: String){
        viewModelTask?.addTask(title,description,timeManager.dayOfWeek.toString())
        showBottomSheet = !showBottomSheet
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun SelectDisplayOption(option: Int){
        when(option){
            1 -> HourSelector(
                modifier = Modifier,
                onDismissRequest = { viewModel.closeBottomSheet() },
                confirmButton =
                    {
                        selectedHour -> var newHour = selectedHour
                        timeManager.ChangeHour(newHour)
                        Log.d("Hour","${newHour}")
                        viewModel.closeBottomSheet()
                    },
                dismissButton = { viewModel.closeBottomSheet() }
            )
            2 -> DateSelector(
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = timeManager,
                composableTimeManager = viewModel
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()){
        Box(modifier = Modifier){
            Column(modifier = Modifier) {
                SelectDay(modifier = Modifier, daySelected, onPreviewsDay = {SelectDayTask(2)}, onAfterDay = {SelectDayTask(1)})
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp)){
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp),) {
                        items(items = localTasks, key = {task -> task.id}) { task ->
                            CardsForTasks(modifier = Modifier, task.title, task.description)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            Button(
                onClick = {
                    showBottomSheet = true
                    timeManager.ReturnActualTime()
                },
                modifier = Modifier.size(60.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add a task", modifier = Modifier.fillMaxSize())
            }
        }
        if(showBottomSheet){
            BottomSheetTasks(
                modifier = Modifier,
                sheetState = sheetState,
                DismissRequest = { changeStateButtomSheet() },
                OpenTimeSelector = {
                        option -> var options = option
                    optionDatePicker = options
                    viewModel.openBottomSheet()
                    Log.d("DATE","${option} hola")
                },
                timeManager = timeManager,
                DoneRequest = {title, description ->
                    DoneAddTasks(title, description)}
            )
            if(viewModel.showAlert){
                SelectDisplayOption(optionDatePicker)
            }

        }

    }
}
