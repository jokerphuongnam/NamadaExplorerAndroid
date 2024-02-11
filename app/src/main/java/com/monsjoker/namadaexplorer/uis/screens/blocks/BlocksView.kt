@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Block
import com.monsjoker.namadaexplorer.uis.shared_view.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocksView(navController: NavController, viewModel: BlocksViewModel = hiltViewModel()) {
    val pagingValidators = viewModel.pagingData.collectAsLazyPagingItems()

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
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        val now = Date()
        PagingStateView(
            pagingItems = pagingValidators,
            contentPadding = innerPadding
        ) { index, block ->
            BlockView(index = index + 1, now = now, block = block)
        }
    }
}

@Composable
private fun LazyListScope.BlocksContent(
    blocks: List<Block>,
    onLoadMore: () -> Unit
) {
    val now = Date()

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            blocks,
            key = { index, block ->
                index
            },
        ) { index, block ->
            BlockView(index = index + 1, now = now, block = block)

            if (blocks.size - 10 < index) {
                onLoadMore()
            }
        }
        item {
//            LoadMoreView(loadMoreState = loadMoreState, retryAction = onLoadMore)
        }
    }
}