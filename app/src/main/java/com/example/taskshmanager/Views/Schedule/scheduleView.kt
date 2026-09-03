package com.example.taskshmanager.Views.Schedule

import android.content.Context
import android.os.Build
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskshmanager.Views.ComposableTimeManager
import com.example.taskshmanager.Views.TimeManager
import com.example.taskshmanager.Views.composables.DateSelector
import com.example.taskshmanager.Views.composables.HourSelector
import com.example.taskshmanager.Views.composables.SelectDay
import com.example.taskshmanager.Views.composables.TimeSelector
import com.example.taskshmanager.data.Domain.Schedule
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardForSchedule(
    modifier: Modifier = Modifier,
    title: String,r
    description: String,
    startTime: String,
    endTime: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val accentColor = MaterialTheme.colorScheme.tertiary

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth().height(115.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(8.dp)
                .background(accentColor))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
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
                                text = "$startTime - $endTime",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onEditClick,
                        Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            "Edit class",
                            Modifier.fillMaxSize(),
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDeleteClick, Modifier.size(28.dp)) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete class",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSchedule(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    DismissRequest: () -> Unit,
    OpenTimeSelector: (option: Int, optionTime: Int) -> Unit,
    timeManagerStart: TimeManager,
    timeManagerFinish: TimeManager,
    scheduleManager: scheduleManager,
    DoneRequest: () -> Unit
) {
    val titleState = rememberTextFieldState(initialText = scheduleManager.title)
    val descriptionState = rememberTextFieldState(initialText = scheduleManager.description)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { DismissRequest() }
    ) {
        Box(modifier = Modifier
            .height(450.dp)
            .fillMaxWidth()
            .padding(15.dp)) {
            Column {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = titleState,
                        label = { Text("Materia") },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(25)
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = descriptionState,
                        label = { Text("Profesor / Aula") },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(50)
                    )
                    Column {
                        Text("Start")
                        TimeSelector(
                            modifier = Modifier,
                            OpenTimeSelector = { option, optionTime ->
                                OpenTimeSelector(
                                    option,
                                    optionTime
                                )
                            },
                            timeManager = timeManagerStart,
                            timeManagerFinish = timeManagerFinish,
                            optionTime = 0
                        )
                        Text("End")
                        TimeSelector(
                            modifier = Modifier,
                            OpenTimeSelector = { option, optionTime ->
                                OpenTimeSelector(
                                    option,
                                    optionTime
                                )
                            },
                            timeManager = timeManagerStart,
                            timeManagerFinish = timeManagerFinish,
                            optionTime = 1
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End)
                ) {
                    Button(onClick = { DismissRequest() }) { Text("Cancelar") }
                    Button(onClick = {
                        scheduleManager.UpdateInfo(titleState.text.toString(), descriptionState.text.toString(), scheduleManager.id)
                        DoneRequest()
                    }
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleViewUI(
    modifier: Modifier = Modifier,
    days: List<String>,
    timeManagerStart: TimeManager = remember { TimeManager() },
    timeManagerFinish: TimeManager = remember { TimeManager() },
    scheduleManager: scheduleManager = viewModel(),
    viewModel: ComposableTimeManager = viewModel(),
    scheduleViewModel: ScheduleViewModel? = null,
    context: Context = LocalContext.current
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var editOption by remember { mutableStateOf(false) }
    var optionDatePicker by remember { mutableIntStateOf(0) }
    var optionTimes by remember { mutableIntStateOf(0) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var indexSelection by remember { mutableIntStateOf(timeManagerStart.currentDayIndex) }
    var daySelected = days[indexSelection]

    val schedulesState = scheduleViewModel?.scheduleState?.collectAsState(initial = emptyList())
    val schedules = schedulesState?.value
    val localSchedules = remember { mutableStateListOf<Schedule>() }

    if (schedules?.isNotEmpty() == true) {
        localSchedules.clear()
        val newList = schedules.filter { it.day.lowercase() == daySelected.lowercase() }
        localSchedules.addAll(newList)
    } else {
        localSchedules.clear()
    }

    fun SelectDayTask(option: Int) {
        when (option) {
            1 -> if (indexSelection < days.size - 1) indexSelection++ else indexSelection = 0
            2 -> if (indexSelection > 0) indexSelection-- else indexSelection = days.size - 1
        }
        daySelected = days[indexSelection]
    }

    fun DoneSchedules() {
        val startMillis: Long = timeManagerStart.getSelectedTimeInMillis()
        val endMillis: Long = timeManagerFinish.getSelectedTimeInMillis()
        val dayOfWeekStr = timeManagerStart.dayOfWeek.toString()

        if (editOption) {
            scheduleViewModel?.editSchedule(
                scheduleManager.id,
                scheduleManager.title,
                scheduleManager.description,
                dayOfWeekStr,
                startMillis,
                endMillis,
                context
            )
            editOption = false
        } else {
            scheduleViewModel?.addSchedule(
                scheduleManager.title,
                scheduleManager.description,
                dayOfWeekStr,
                startMillis,
                endMillis,
                context
            )
        }

        indexSelection = timeManagerStart.CheckIndex()
        showBottomSheet = false
    }

    fun EditSchedule(timeStart: TimePickerState, dateStart: LocalDate, timeEnd: TimePickerState, dateEnd: LocalDate, schedule: Schedule) {
        scheduleManager.UpdateInfo(schedule.subject, schedule.description, schedule.id)
        timeManagerStart.ChangeHour(timeStart)
        timeManagerStart.ChangeDate(dateStart)
        timeManagerFinish.ChangeHour(timeEnd)
        timeManagerFinish.ChangeDate(dateEnd)
        showBottomSheet = true
        editOption = true
    }

    @Composable
    fun SelectDisplayOption(option: Int, optionTime: Int) {
        when (option) {
            1 -> HourSelector(
                modifier = Modifier,
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = if (optionTime == 1) timeManagerFinish else timeManagerStart,
                composableTimeManager = viewModel
            )
            2 -> DateSelector(
                onDismissRequest = { viewModel.closeBottomSheet() },
                dismissButton = { viewModel.closeBottomSheet() },
                timeManager = if (optionTime == 1) timeManagerFinish else timeManagerStart,
                composableTimeManager = viewModel
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            SelectDay(modifier = Modifier, daySelected = daySelected, onPreviewsDay = { SelectDayTask(2) }, onAfterDay = { SelectDayTask(1) })
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 15.dp), verticalArrangement = Arrangement.spacedBy(25.dp), contentPadding = PaddingValues(bottom = 88.dp)) {
                items(items = localSchedules, key = { schedule -> schedule.id }) { schedule ->
                    val startDateTime = timeManagerStart.setTimeFromMillis(schedule.startTimeMillis)
                    val finishDateTime = timeManagerFinish.setTimeFromMillis(schedule.endTimeMillis)
                    val startStr = "${String.format("%02d", startDateTime.hour)}:${String.format("%02d", startDateTime.minute)}"
                    val endStr = "${String.format("%02d", finishDateTime.hour)}:${String.format("%02d", finishDateTime.minute)}"

                    CardForSchedule(
                        title = schedule.subject,
                        description = schedule.description,
                        startTime = startStr,
                        endTime = endStr,
                        onEditClick = {
                            val timeStart = TimePickerState(initialHour = startDateTime.hour, initialMinute = startDateTime.minute, is24Hour = true)
                            val dateStart = LocalDate.of(startDateTime.year, startDateTime.month, startDateTime.dayOfMonth)
                            val timeEnd = TimePickerState(initialHour = finishDateTime.hour, initialMinute = finishDateTime.minute, is24Hour = true)
                            val dateEnd = LocalDate.of(finishDateTime.year, finishDateTime.month, finishDateTime.dayOfMonth)
                            EditSchedule(timeStart, dateStart, timeEnd, dateEnd, schedule)
                        },
                        onDeleteClick = { scheduleViewModel?.deleteSchedule(schedule, context) }
                    )
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            Button(onClick = {
                editOption = false
                scheduleManager.EmptyInfo()
                timeManagerStart.ReturnActualTime()
                timeManagerFinish.ReturnActualTime()
                showBottomSheet = true
            }, modifier = Modifier.size(60.dp), contentPadding = PaddingValues(0.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add a class", modifier = Modifier.fillMaxSize())
            }
        }

        if (showBottomSheet) {
            BottomSheetSchedule(
                sheetState = sheetState,
                DismissRequest = { showBottomSheet = false },
                OpenTimeSelector = {option, optionTime ->
                    optionDatePicker = option;
                    optionTimes = optionTime;
                    viewModel.openBottomSheet() },
                timeManagerStart = timeManagerStart,
                timeManagerFinish = timeManagerFinish,
                scheduleManager = scheduleManager,
                DoneRequest = { DoneSchedules() }
            )
        }
        if (viewModel.showAlert) {
            SelectDisplayOption(option = optionDatePicker, optionTime = optionTimes)
        }
    }
}