package com.cd.utility.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cd.utility.color.primaryColor

object ComposeUtils {

    @Composable
    fun boxLoading(){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1f)
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            Column {
                CircularProgressIndicator(
                    modifier =
                    Modifier.padding(6.dp, 0.dp, 0.dp, 0.dp),
                    color = primaryColor
                )
                Text(text = "Loading...", Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))
            }
        }
    }
}