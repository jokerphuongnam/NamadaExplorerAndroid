@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.transactions.data.TransactionState
import com.monsjoker.namadaexplorer.uis.screens.transactions.views.BondsView
import com.monsjoker.namadaexplorer.uis.screens.transactions.views.TransfersView
import com.monsjoker.namadaexplorer.utils.visibility

@Composable
fun TransactionsScreen(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val selectedTab = remember { mutableStateOf(TransactionState.TRANSFERS) }
    val transactionLoadedState by rememberSaveable {
        mutableStateOf(
            HashSet<TransactionState>().also {
                it.add(TransactionState.TRANSFERS)
            }
        )
    }

    val pullToRefreshState = rememberPullToRefreshState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier,
        ) {
            TabRow(
                selectedTabIndex = selectedTab.value.ordinal,
                modifier = Modifier.height(52.dp)
            ) {
                TransactionState.entries.forEach { state ->
                    Tab(
                        text = {
                            Text(
                                text = state.rawValue
                            )
                        },
                        selected = selectedTab.value == state,
                        onClick = {
                            selectedTab.value = state
                            transactionLoadedState.add(state)
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .clip(RoundedCornerShape(0.dp))
            ) {
                if (transactionLoadedState.contains(TransactionState.TRANSFERS)) {
                    val transfersPagingData =
                        viewModel.transfersPagingData.collectAsLazyPagingItems()

                    LaunchedEffect(pullToRefreshState.isRefreshing) {
                        if (pullToRefreshState.isRefreshing) {
                            transfersPagingData.refresh()
                        }
                    }

                    LaunchedEffect(transfersPagingData.loadState.refresh !is LoadState.Loading) {
                        if (transfersPagingData.loadState.refresh !is LoadState.Loading) {
                            pullToRefreshState.endRefresh()
                        }
                    }

                    TransfersView(
                        pagingItems = transfersPagingData,
                        modifier = Modifier.visibility(selectedTab.value == TransactionState.TRANSFERS)
                    )
                }
                if (transactionLoadedState.contains(TransactionState.BONDS)) {
                    val bondsPagingData = viewModel.bondsPagingData.collectAsLazyPagingItems()

                    LaunchedEffect(pullToRefreshState.isRefreshing) {
                        if (pullToRefreshState.isRefreshing) {
                            bondsPagingData.refresh()
                        }
                    }

                    LaunchedEffect(bondsPagingData.loadState.refresh !is LoadState.Loading) {
                        if (bondsPagingData.loadState.refresh !is LoadState.Loading) {
                            pullToRefreshState.endRefresh()
                        }
                    }

                    BondsView(
                        pagingItems = bondsPagingData,
                        modifier = Modifier.visibility(selectedTab.value == TransactionState.BONDS)
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
}