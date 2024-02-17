package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class ProgressBarValue(
    val value: Float,
    val color: Color
)

@Composable
fun ProgressBarMultipleValue(values: List<ProgressBarValue>, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxHeight() then modifier) {
        for (value in values) {
            Box(
                modifier = Modifier
                    .weight(
                        value.value / values
                            .map { it.value }
                            .sum()
                    )
                    .background(value.color)
                    .fillMaxHeight()
            )
        }
    }
}