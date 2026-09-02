package com.example.taskshmanager.Views.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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