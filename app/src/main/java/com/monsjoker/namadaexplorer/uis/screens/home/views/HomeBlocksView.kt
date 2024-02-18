package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockShimmerView
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import java.util.Date

@Composable
fun HomeBlocksView(
    dataState: DataState<List<Block>>,
    navController: NavController,
    onRetry: (() -> Unit)? = null
) {
    val now = Date()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            when (dataState) {
                is DataState.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (index in 0..9) {
                            BlockShimmerView()
                        }
                    }
                }

                is DataState.Success -> BlocksView(
                    navController = navController,
                    dataState.data,
                    now,
                )

                is DataState.Error -> ErrorView(error = dataState.error, onRetry = onRetry)
            }
        }
    }
}

@Composable
private fun BlocksView(
    navController: NavController,
    blocks: List<Block>,
    now: Date,
) {
    if (blocks.isEmpty()) {
        Text("Block is empty", fontWeight = FontWeight.Bold)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(blocks) { index, block ->
                BlockView(
                    index = index + 1,
                    navController = navController,
                    now = now,
                    block = block,
                    modifier = Modifier
                )
            }
        }
    }
}