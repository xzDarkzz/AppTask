package com.example.taskshmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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