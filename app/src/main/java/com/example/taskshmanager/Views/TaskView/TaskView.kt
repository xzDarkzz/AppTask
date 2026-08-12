package com.example.taskshmanager.Views.TaskView

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.TimePickerDisplayMode
import androidx.compose.material3.TimePickerState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.ui.TaskViewModel
import java.time.LocalDate

//Select The Day Of the tasks
@Composable
fun SelectDay(modifier: Modifier, daySelected: String, onPreviewsDay : () -> Unit, onAfterDay : () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(150.dp)){
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = daySelected,
                fontSize = 25.sp,
                color = Color.LightGray
            )
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
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsForTasks(
    modifier: Modifier,
    task: Task,
    EditTask: (time: TimePickerState,date: LocalDate, task: Task) -> Unit,
    DoneTask: (task: Task) -> Unit


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
                                text = "${task.dayOfTheMonth}/${task.month}/${task.year}",
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
                                text = "${task.hour}:${task.minute}",
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
                        val time = TimePickerState(
                            initialHour = task.hour.toInt(),
                            initialMinute = task.minute.toInt(),
                            is24Hour = true
                        )
                        val date = java.time.LocalDate.of(
                            task.year.toInt(),            // Año de la tarea actual
                            task.month.toInt(),           // Mes de la tarea actual
                            task.dayOfTheMonth.toInt()    // Día de la tarea actual
                        )
                        EditTask(time,date, task)
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

//HourSelector


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourSelector(
    modifier: Modifier,
    onDismissRequest : () -> Unit,
    dismissButton : () -> Unit,
    timeManager: TimeManager,
    composableTimeManager: ComposableTimeManager
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
            Text(
                "${String.format("%02d", timeManager.dayOfMonth)}/${
                    String.format(
                        "%02d",
                        timeManager.month
                    )
                }",
                fontSize = 35.sp
            )
        }
        VerticalDivider(thickness = 5.dp)
        TextButton(onClick = {OpenTimeSelector(1)}) {
            Text(
                "${String.format("%02d", timeManager.hour)}:${
                    String.format(
                        "%02d",
                        timeManager.minute
                    )
                }",
                fontSize = 35.sp
            )
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
    DoneRequest : () -> Unit,
    taskManager: TaskManagerInformation
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




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(
    modifier: Modifier = Modifier,
    days: List<String>,
    viewModel: ComposableTimeManager = viewModel(),
    timeManager: TimeManager = viewModel(),
    viewModelTask: TaskViewModel? = null,
    taskManager : TaskManagerInformation = viewModel()
){

    //Carga de todas las tareas
    val tasksState = viewModelTask?.tasksState?.collectAsState(initial = emptyList())
    val tasks = tasksState?.value
    val localTasks = remember { mutableStateListOf<Task>() }

    //BottomSheet to add new task
    var showBottomSheet by remember { mutableStateOf(false) }
    var editOption by remember { mutableStateOf(false) }
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

    fun DoneTasks(){
        if(editOption){
            viewModelTask?.editTask(
                id = taskManager.id,
                title = taskManager.title,
                description = taskManager.description,
                day = timeManager.dayOfWeek.toString(),
                dayOfTheMonth = String.format("%02d",timeManager.dayOfMonth),
                month = String.format("%02d",timeManager.month),
                year = timeManager.year.toString(),
                hour = String.format("%02d",timeManager.hour),
                minute = String.format("%02d",timeManager.minute),
            )
            Log.d("editTask","" +
                    "el titulo: ${taskManager.title}, " +
                    "dia: ${timeManager.dayOfWeek}," +
                    "la hora: ${timeManager.hour} " +
                    "Su id : ${taskManager.id}")
            editOption = false
        }else{
            viewModelTask?.addTask(
                title = taskManager.title,
                description = taskManager.description,
                day = timeManager.dayOfWeek.toString(),
                dayOfTheMonth = String.format("%02d",timeManager.dayOfMonth),
                month = String.format("%02d",timeManager.month),
                year = timeManager.year.toString(),
                hour = String.format("%02d",timeManager.hour),
                minute = String.format("%02d",timeManager.minute),
            )
        }

        showBottomSheet = false
    }

    fun EditTask(time: TimePickerState,date: LocalDate, task: Task){
        taskManager.UpdateInfo(task.title,task.description,task.id)
        timeManager.ChangeHour(time)
        timeManager.ChangeDate(date)
        showBottomSheet = true
        editOption = true
    }
    fun DoneTask(task: Task){
        viewModelTask?.deleteTask(task)
    }

    @Composable
    fun SelectDisplayOption(option: Int){
        when(option){
            1 -> HourSelector(
                modifier = Modifier,
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = timeManager,
                composableTimeManager = viewModel
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
                            CardsForTasks(
                                modifier = Modifier,
                                task = task,
                                EditTask = {time, date, task->
                                    EditTask(time,date, task)
                                },
                                DoneTask = {task ->
                                    DoneTask(task)
                                }
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            Button(
                onClick = {
                    editOption = false
                    showBottomSheet = true
                    timeManager.ReturnActualTime()
                    taskManager.EmptyInfo()

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
                DoneRequest = {
                    DoneTasks()
                },
                taskManager
            )
            if(viewModel.showAlert){
                SelectDisplayOption(optionDatePicker)
            }

        }

    }
}


