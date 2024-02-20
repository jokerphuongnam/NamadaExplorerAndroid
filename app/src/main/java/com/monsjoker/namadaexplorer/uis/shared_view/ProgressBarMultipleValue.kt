package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class ProgressBarValue(
    val value: Long,
    val color: Color
)

@Composable
fun ProgressBarMultipleValue(values: List<ProgressBarValue>, modifier: Modifier = Modifier) {
    val sum = values.map { it.value }.sum()
    Row(modifier = Modifier.fillMaxHeight() then modifier) {
        for (value in values) {
            val weight = value.value.toDouble() / sum.toDouble()
            if (weight > 0) {
                Box(
                    modifier = Modifier
                        .weight(weight.toFloat())
                        .background(value.color)
                        .fillMaxHeight()
                )
            }
        }
    }
}