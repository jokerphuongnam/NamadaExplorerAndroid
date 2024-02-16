@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.monsjoker.namadaexplorer.uis.screens.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockBottomSheetView
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocksView(navController: NavController, viewModel: BlocksViewModel = hiltViewModel()) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val sheetState = rememberModalBottomSheetState()
    var blockSelected by rememberSaveable {
        mutableStateOf<Block?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Blocks")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        val now = Date()
        PagingStateView(
            pagingItems = pagingData,
            contentPadding = innerPadding,
            emptyText = "Block is empty"
        ) { index, block ->
            BlockView(
                index = index + 1,
                now = now,
                block = block,
                modifier = Modifier.clickable {
                    blockSelected = block
                }
            )
        }
    }

    if (blockSelected != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                blockSelected = null
            }
        ) {
            BlockBottomSheetView(
                navController = navController,
                block = blockSelected!!
            ) {
                blockSelected = null
            }
        }
    }
}