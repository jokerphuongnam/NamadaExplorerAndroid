package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetSelectedView(
    title: String,
    text: String,
    isBoolContent: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$title:",
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = if (onClick == null) {
                Modifier
            } else {
                Modifier
                    .background(Color.Cyan)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .clickable {
                        onClick.invoke()
                    }
            } then modifier
        ) {
            Box(
                modifier = if (onClick == null) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                }
            ) {
                Text(
                    text = text,
                    fontWeight = if (isBoolContent) FontWeight.Bold else null
                )
            }
        }
    }
}