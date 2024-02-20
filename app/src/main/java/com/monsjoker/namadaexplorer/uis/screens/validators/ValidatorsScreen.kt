@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.validators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorShimmerView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView

@Composable
fun ValidatorsScreen(
    navController: NavController,
    viewModel: ValidatorsViewModel = hiltViewModel()
) {
    val homeDetailsState = viewModel.homeDetailsState
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            pagingData.refresh()
            viewModel.loadHomeDetails()
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
            PagingStateView(
                pagingItems = pagingData,
                emptyText = "Validator Empty",
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 16.dp),
                header = {
                    Box(modifier = Modifier.padding(vertical = 32.dp)) {
                        HomeDetailsView(
                            dataState = homeDetailsState,
                            onRetry = { viewModel.loadHomeDetails() },
                        )
                    }
                },
                loading = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (index in 0..9) {
                            ValidatorShimmerView()
                        }
                    }
                },
                loadMoreLoading = {
                    ValidatorShimmerView()
                }
            ) { index, validator ->
                ValidatorView(
                    index = index + 1,
                    validator = validator,
                    modifier = Modifier
                ) {
                    navBackStackEntry?.savedStateHandle?.set(
                        "validator_address",
                        validator.address
                    )
                    navController.navigate("validator")
                }
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