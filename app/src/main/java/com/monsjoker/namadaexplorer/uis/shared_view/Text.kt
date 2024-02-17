package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun Text(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}

@Composable
fun Text(label: String, value: Any) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold
        )
        Text(text = "$value")
    }
}

@Composable
fun Text(label: String, value: Number) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold
        )
        Text(text = value.formattedWithCommas())
    }
}