package com.monsjoker.namadaexplorer.uis.screens.validators.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.MiddleEllipsisText
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun ValidatorView(
    modifier: Modifier = Modifier,
    index: Int,
    validator: Validator,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = {
            onClick?.invoke()
        },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp)
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

@Composable
fun ValidatorShimmerView() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    ComponentRectangleLine(width = 70.dp, height = 15.dp)
                }
                ComponentRectangleLineFullWidth()
                Row(verticalAlignment = Alignment.Bottom) {
                    ComponentRectangleLine()
                    Spacer(modifier = Modifier.weight(1f))
                    ComponentRectangleLine()
                }
            }
        }
    }
}