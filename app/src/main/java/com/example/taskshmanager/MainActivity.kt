package com.example.taskshmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@Composable
fun Tasks(modifier: Modifier = Modifier, days: List<String>){
    var indexSelection by remember {mutableIntStateOf(0)}
    var daySelected = days[indexSelection]
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
    Box(modifier = modifier.fillMaxSize()){
        Box(modifier = Modifier){
            Column(modifier = Modifier) {
                Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = Modifier.fillMaxHeight().width(150.dp)){
                        Text(modifier = Modifier.align(Alignment.CenterStart), text = daySelected, fontSize = 25.sp, color = Color(0XFF464858))
                    }
                    //Buttoms to changes the Day
                    Box(modifier = Modifier){
                        Row(modifier = Modifier) {

                            IconButton(onClick = {SelectDayTask(2)}, modifier = Modifier) {
                                Icon(
                                    Icons.Filled.ArrowBackIosNew,
                                    contentDescription = "Before Botton"
                                )
                            }
                            IconButton(onClick = {SelectDayTask(1)}, modifier = Modifier) {
                                Icon(
                                    Icons.Filled.ArrowForwardIos,
                                    contentDescription = "After Botton"
                                )
                            }
                        }
                    }
                }
                Box(modifier){

                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)){
            Button(onClick = {}, modifier = Modifier.size(60.dp), contentPadding = PaddingValues(0.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add a task", modifier = Modifier.fillMaxSize())
            }
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