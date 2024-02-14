package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeDetailsData
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun HomeDetailsView(dataState: DataState<HomeDetailsData>, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when (dataState) {
            is DataState.Loading -> ProgressView()
            is DataState.Success -> DataView(dataState.data)
            is DataState.Error -> ErrorView(error = dataState.error, onRetry = onRetry)
        }
    }
}

@Composable
private fun DataView(data: HomeDetailsData) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(label = "Epoch", value = data.epoch)
        Text(label = "Block Height", value = data.blockHeight)
        Text(label = "Total Stake", value = "${(data.totalStake.toDouble() / 1_000_000).formattedWithCommas()} NAM")
        Text(label = "Validators", value = data.validators)
        Text(label = "Governance Proposals", value = data.governanceProposals)
        Text(label = "Chain ID", value = Constants.chainID)
        Spacer(modifier = Modifier.fillMaxHeight())
    }
}