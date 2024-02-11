package com.monsjoker.namadaexplorer.uis.screens.validator_details.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import java.util.Date


fun LazyListScope.validatorDetailBlocks(
    dataState: DataState<List<Block>>,
    onRetry: (() -> Unit)? = null
) {
    when (dataState) {
        is DataState.Loading -> {
            item {
                ProgressView()
            }
        }

        is DataState.Success -> {
            val now = Date()
            dataView(dataState.data, now = now)
        }

        is DataState.Error -> {
            item {
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    ErrorView(error = dataState.error, onRetry = onRetry)
                }
            }
        }
    }
}

private fun LazyListScope.dataView(data: List<Block>, now: Date) {
    if (data.isEmpty()) {
        item {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    text = "validator not have blocks",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        itemsIndexed(data) { index, block ->
            BlockView(index = index + 1, now = now, block = block)
        }
    }
}