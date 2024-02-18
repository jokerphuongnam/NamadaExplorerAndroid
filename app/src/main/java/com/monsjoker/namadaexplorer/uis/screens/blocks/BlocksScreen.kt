package com.monsjoker.namadaexplorer.uis.screens.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockShimmerView
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocksScreen(navController: NavController, viewModel: BlocksViewModel = hiltViewModel()) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        val now = Date()
        PagingStateView(
            pagingItems = pagingData,
            emptyText = "Block is empty",
            contentPadding = PaddingValues(vertical = 16.dp),
            loading = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (index in 0..9) {
                        BlockShimmerView()
                    }
                }
            }
        ) { index, block ->
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