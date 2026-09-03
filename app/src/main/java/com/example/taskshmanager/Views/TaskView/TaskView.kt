package com.example.taskshmanager.Views.TaskView

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskshmanager.Views.ComposableTimeManager
import com.example.taskshmanager.Views.TaskView.Managers.TaskManagerInformation
import com.example.taskshmanager.Views.TaskView.taskComposables.BottomSheetTasks
import com.example.taskshmanager.Views.TaskView.taskComposables.CardsForTasks
import com.example.taskshmanager.Views.TimeManager
import com.example.taskshmanager.Views.composables.DateSelector
import com.example.taskshmanager.Views.composables.HourSelector
import com.example.taskshmanager.Views.composables.SelectDay
import com.example.taskshmanager.data.Domain.Task
import com.example.taskshmanager.ui.TaskViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(
    modifier: Modifier = Modifier,
    days: List<String>,
    viewModel: ComposableTimeManager = viewModel(),
    timeManager: TimeManager = remember { TimeManager() },
    timeManagerFinish: TimeManager = remember { TimeManager() },
    viewModelTask: TaskViewModel? = null,
    taskManager : TaskManagerInformation = viewModel(),
    permissionStatus : Boolean,
    SendPermissionNotification : () -> Unit,
    context: Context
){

    //Carga de todas las tareas
    val tasksState = viewModelTask?.tasksState?.collectAsState(initial = emptyList())
    val tasks = tasksState?.value
    val localTasks = remember { mutableStateListOf<Task>() }

    //BottomSheet to add new tasks
    var showBottomSheet by remember { mutableStateOf(false) }
    var editOption by remember { mutableStateOf(false) }
    var optionDatePicker by remember { mutableIntStateOf(0)}
    var optionTimeTask by remember { mutableIntStateOf(0)}
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    //Operator tu indicate the UI day
    var indexSelection by remember {mutableIntStateOf(timeManager.currentDayIndex)}
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



    //Select an a Day
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

    //Add the task to the local database and Make the notification

    fun DoneTasks(){
        var id: Long?
        val dateMillis: Long = timeManager.getSelectedTimeInMillis()
        val endDateMillis : Long = timeManagerFinish.getSelectedTimeInMillis()
        Log.d("miliiss", "${dateMillis}")
        //Mandar a la base de datos
        if(editOption){
            viewModelTask?.editTask(
                id = taskManager.id,
                title = taskManager.title,
                description = taskManager.description,
                day = timeManagerFinish.dayOfWeek.toString(),
                dueDateMillis = dateMillis,
                dueDateEndMillis = endDateMillis,
                context
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
                day = timeManagerFinish.dayOfWeek.toString(),
                dueDateMillis = dateMillis,
                dueDateEndMillis = endDateMillis,
                context
            )
        }

        //EXPERIENCIA UI/UX
        indexSelection = timeManager.CheckIndex()


        showBottomSheet = false
    }

    fun EditTask(timeStart: TimePickerState,dateStart: LocalDate,timeEnd: TimePickerState, dateEnd: LocalDate,task: Task){
        taskManager.UpdateInfo(task.title,task.description,task.id)
        timeManager.ChangeHour(timeStart)
        timeManager.ChangeDate(dateStart)
        timeManagerFinish.ChangeHour(timeEnd)
        timeManagerFinish.ChangeDate(dateEnd)
        showBottomSheet = true
        editOption = true
    }
    fun DoneTask(task: Task){
        viewModelTask?.deleteTask(task)
    }

    @Composable
    fun SelectDisplayOption(option: Int, optionTime: Int){
        SendPermissionNotification()
        when(option){
            1 -> HourSelector(
                modifier = Modifier,
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = if(optionTime == 1) timeManagerFinish else timeManager,
                composableTimeManager = viewModel
            )
            2 -> DateSelector(
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = if(optionTime == 1) timeManagerFinish else timeManager,
                composableTimeManager = viewModel
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()){
        Box(modifier = Modifier){
            Column(modifier = Modifier) {
                SelectDay(modifier = Modifier, daySelected, onPreviewsDay = {SelectDayTask(2)}, onAfterDay = {SelectDayTask(1)})
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(top = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(items = localTasks, key = {task -> task.id}) { task ->
                        val dateTime = timeManager.setTimeFromMillis(task.dueDateMillis)
                        val finishDateTime = timeManagerFinish.setTimeFromMillis(task.dueDateEndMillis)
                        CardsForTasks(
                            modifier = Modifier,
                            task = task,
                            EditTask = {timeStart, dateStart, timeEnd, dateEnd, task->
                                EditTask(timeStart,dateStart,timeEnd,dateEnd, task)
                            },
                            DoneTask = {task ->
                                DoneTask(task)
                            },
                            dateTime,
                            finishDateTime
                        )
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
                    timeManagerFinish.ReturnActualTime()
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
                        option, optionTime ->
                    optionDatePicker = option
                    optionTimeTask = optionTime
                    viewModel.openBottomSheet()
                },
                timeManager = timeManager,
                DoneRequest = {
                    DoneTasks()
                },
                taskManager,
                timeManagerFinish
            )
            if(viewModel.showAlert){
                SelectDisplayOption(optionDatePicker, optionTimeTask)
            }

        }

    }
}



