@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.governance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import com.monsjoker.namadaexplorer.uis.screens.governance.views.ProposalDetailStateView
import com.monsjoker.namadaexplorer.uis.screens.governance.views.ProposalShimmerView
import com.monsjoker.namadaexplorer.uis.screens.governance.views.ProposalView
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView

@Composable
fun GovernanceScreen(
    navController: NavController,
    viewModel: GovernanceViewModel = hiltViewModel()
) {
    val proposalsState = viewModel.proposalsState

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.loadProposals()
        }
    }

    LaunchedEffect(proposalsState.isLoading) {
        if (!proposalsState.isLoading) {
            pullToRefreshState.endRefresh()
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
        ) {
            ProposalsStateView(
                dataState = proposalsState,
                header = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ProposalDetailStateView(dataState = proposalsState)
                    }
                }
            ) {
                viewModel.loadProposals()
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

@Composable
private fun ProposalsStateView(
    dataState: DataState<List<Proposal>>,
    header: @Composable (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null
) {
    when (dataState) {
        is DataState.Loading -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ) {
                header?.invoke()

                Box(modifier = Modifier.padding(all = 12.dp)) {
                    Text(
                        text = "Proposals",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }

                for (index in 0..9) {
                    ProposalShimmerView()
                }
            }
        }

        is DataState.Success -> {
            ProposalsView(data = dataState.data, header = header)
        }

        is DataState.Error -> {
            ErrorView(
                error = dataState.error,
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun ProposalsView(
    data: List<Proposal>,
    header: @Composable (() -> Unit)? = null,
) {
    if (data.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            header?.invoke()

            Text(
                text = "Proposal is empty",
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                header?.invoke()
            }

            item {
                Box(modifier = Modifier.padding(all = 12.dp)) {
                    Text(
                        text = "Proposals",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }

            itemsIndexed(data) { index, proposal ->
                ProposalView(index = index + 1, proposal = proposal)
            }
        }
    }
}