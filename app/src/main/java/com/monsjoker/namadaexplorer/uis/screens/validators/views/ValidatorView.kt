package com.monsjoker.namadaexplorer.uis.screens.validators.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.MiddleEllipsisText
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun ValidatorView(modifier: Modifier = Modifier, index: Int, validator: Validator) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(Color.Yellow)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) then modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Column {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Priority: ${validator.proposerPriority}",
                        fontSize = 10.sp
                    )
                }
                MiddleEllipsisText(
                    text = validator.address!!,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = validator.height!!.formattedWithCommas())
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = (validator.votingPower / 1_000_000).formattedWithCommas())
                }
            }
        }
    }
}
