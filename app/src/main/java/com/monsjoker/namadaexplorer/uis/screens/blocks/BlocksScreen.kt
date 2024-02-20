@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockShimmerView
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocksScreen(navController: NavController, viewModel: BlocksViewModel = hiltViewModel()) {
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            pagingData.refresh()
        }
    }

    LaunchedEffect(pagingData.loadState.refresh !is LoadState.Loading) {
        if (pagingData.loadState.refresh !is LoadState.Loading) {
            pullToRefreshState.endRefresh()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            val now = Date()
            PagingStateView(
                pagingItems = pagingData,
                emptyText = "Block is empty",
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                loading = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (index in 0..9) {
                            BlockShimmerView()
                        }
                    }
                },
                loadMoreLoading = {
                    BlockShimmerView()
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                PullToRefreshContainer(
                    state = pullToRefreshState,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}