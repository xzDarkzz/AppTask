package com.example.taskshmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.taskshmanager.ui.theme.TaskShManagerTheme
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskShManagerTheme {
                App(modifier = Modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navController = rememberNavController()
    val DaysOfTheWeek = listOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday", "Sunday")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(fontSize = 25.sp,
                        text = "Task",
                        color = Color.White
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(88.dp)
                    .padding(horizontal = 48.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(32.dp)),
                // Para una barra flotante personalizada, desactivar insets automáticos
                // dentro de la NavigationBar evita que el sistema le agregue relleno no deseado abajo:
                windowInsets = WindowInsets(0)
            ){
                NavigationBarItem(
                    selected = true,
                    onClick = {
                    },
                    icon = {
                        Icon(
                            Icons.Default.Checklist,
                            contentDescription = "Nose",
                        )
                    },
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {
                    },
                    icon = {
                        Icon(
                            Icons.Default.School,
                            contentDescription = "Nose",
                        )
                    },
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {
                    },
                    icon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Nose",
                        )
                    },
                )
            }
        },
    ) { innerPadding ->
        Tasks(modifier = Modifier
            .padding(innerPadding)
            .padding(10.dp), days = DaysOfTheWeek)
    }
}

//Select The Day Of the tasks
@Composable
fun SelectDay(modifier: Modifier, daySelected: String, onPreviewsDay : () -> Unit, onAfterDay : () -> Unit){
    Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier.fillMaxHeight().width(150.dp)){
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
fun CardsForTasks(modifier: Modifier){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.fillMaxHeight().width(320.dp)) {
                Text("TITLE", fontSize = 35.sp)
                Text("Description", modifier = Modifier.padding(top = 5.dp))
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete this activity",
                        modifier = Modifier.size(35.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Delete this activity",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTasks(modifier: Modifier,sheetState : SheetState, DismissRequest : () -> Unit){
    //Variables
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    // ------------------------------------- //
    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { DismissRequest()}) {
        Box(modifier= Modifier.height(600.dp).fillMaxWidth().padding(15.dp)){
            Column(modifier = Modifier) {
                Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = rememberTextFieldState("___TITLE____"),
                        label = { Text("TITLE") },
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(15)

                    )
                    HorizontalDivider(thickness = 3.dp)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = rememberTextFieldState("Description"),
                        label = { Text("Description") },
                        lineLimits = TextFieldLineLimits.MultiLine(4),
                        inputTransformation = InputTransformation.maxLength(30)
                    )
                    HorizontalDivider(thickness = 3.dp)

                    Box(modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .padding(10.dp), contentAlignment = Alignment.Center
                        ){
                        TimeInput(
                            state = timePickerState,
                        )
                    }

                }
                Row(modifier = Modifier) {

                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tasks(modifier: Modifier = Modifier, days: List<String>){
    var indexSelection by remember {mutableIntStateOf(0)}
    var daySelected = days[indexSelection]
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
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
    Box(modifier = modifier.fillMaxSize()){
        Box(modifier = Modifier){
            Column(modifier = Modifier) {
                SelectDay(modifier = Modifier, daySelected, onPreviewsDay = {SelectDayTask(2)}, onAfterDay = {SelectDayTask(1)})
                Box(modifier = Modifier.fillMaxSize().padding(top = 15.dp)){
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp),) {
                        items(3) { index ->
                            CardsForTasks(modifier = Modifier)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            Button(
                onClick = {
                    showBottomSheet = true
                    //Para identificar la consola
                    Log.d("MI_APP", "Hola desde el botón!")
                },
                modifier = Modifier.size(60.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add a task", modifier = Modifier.fillMaxSize())
            }
        }
        if(showBottomSheet){
            BottomSheetTasks(modifier = Modifier, sheetState = sheetState, DismissRequest = {changeStateButtomSheet()} )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true, // 👈 Dibuja la barra de estado y de gestos
    device = "spec:width=411dp,height=891dp" // 👈 Simula el tamaño estándar de un teléfono moderno
)
@Composable
fun GreetingPreview() {
    TaskShManagerTheme {
        App(modifier = Modifier)
    }
}