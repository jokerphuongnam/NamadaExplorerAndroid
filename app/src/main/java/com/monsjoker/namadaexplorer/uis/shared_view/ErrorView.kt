package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(error: Exception, onRetry: (() -> Unit)? = null) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = error.toString(),
            color = Color.Red
        )
        Button(
            onClick = { onRetry?.invoke() },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.Yellow, shape = RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
        ) {
            Text(
                text = "Retry",
                color = Color.White
            )
        }
    }
}

