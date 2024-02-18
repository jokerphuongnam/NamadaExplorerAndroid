package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeDetailsData
import com.monsjoker.namadaexplorer.uis.shared_view.CircularChart
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRing
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun HomeDetailsView(dataState: DataState<HomeDetailsData>, onRetry: (() -> Unit)? = null) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            when (dataState) {
                is DataState.Loading -> HomeDetailsShimmerLoadingData()
                is DataState.Success -> DataView(dataState.data)
                is DataState.Error -> ErrorView(error = dataState.error, onRetry = onRetry)
            }
        }
    }
}

@Composable
private fun DataView(data: HomeDetailsData) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Total Stake",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            CircularChart(
                value = ((data.totalStake.toFloat() / 1_000_000) / data.allState.toFloat()) * 100,
                color = MaterialTheme.colorScheme.primaryContainer,
                backgroundCircleColor = Color.Transparent,
                size = 100.dp,
                thickness = 16.dp
            )

            Text(
                text = "${(data.totalStake.toDouble() / 1_000_000).formattedWithCommas()} NAAN / ${data.allState.formattedWithCommas()} NAAN",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Text(label = "Epoch", value = data.epoch)
        Text(label = "Block Height", value = data.blockHeight)
        Text(label = "Validators", value = data.validators)
        Text(label = "Governance Proposals", value = data.governanceProposals)
        Text(label = "Chain ID", value = Constants.chainID)
    }
}

@Composable
private fun HomeDetailsShimmerLoadingData() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row {
                ComponentRectangleLine(width = 120.dp)

                Spacer(modifier = Modifier.weight(1f))
            }

            ComponentRing(
                size = 100.dp,
                thickness = 16.dp
            )

            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                ComponentRectangleLineFullWidth()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ComponentRectangleLine(width = 100.dp)
        ComponentRectangleLine(width = 150.dp)
        ComponentRectangleLine(width = 100.dp)
        ComponentRectangleLine(width = 200.dp)
        ComponentRectangleLine(width = 400.dp)
    }
}