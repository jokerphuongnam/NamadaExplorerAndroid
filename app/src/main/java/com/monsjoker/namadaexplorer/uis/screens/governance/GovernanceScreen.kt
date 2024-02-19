package com.monsjoker.namadaexplorer.uis.screens.governance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import com.monsjoker.namadaexplorer.uis.screens.governance.views.ProposalShimmerView
import com.monsjoker.namadaexplorer.uis.screens.governance.views.ProposalView
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView

@Composable
fun GovernanceScreen(
    navController: NavController,
    viewModel: GovernanceViewModel = hiltViewModel()
) {
    val proposalsState = viewModel.proposalsState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier
        ) {
            ProposalsStateView(dataState = proposalsState)
        }
    }
}

@Composable
private fun ProposalsStateView(
    dataState: DataState<List<Proposal>>,
    onRetry: (() -> Unit)? = null
) {
    when (dataState) {
        is DataState.Loading -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ) {
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
            ProposalsView(data = dataState.data)
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
private fun ProposalsView(data: List<Proposal>) {
    if (data.isEmpty()) {
        Text(
            text = "Proposal is empty",
            fontWeight = FontWeight.Bold
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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