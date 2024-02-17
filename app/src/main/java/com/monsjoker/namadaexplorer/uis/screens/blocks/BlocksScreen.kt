@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.monsjoker.namadaexplorer.uis.screens.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocksScreen(navController: NavController, viewModel: BlocksViewModel = hiltViewModel()) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val now = Date()
        PagingStateView(
            pagingItems = pagingData,
            emptyText = "Block is empty"
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