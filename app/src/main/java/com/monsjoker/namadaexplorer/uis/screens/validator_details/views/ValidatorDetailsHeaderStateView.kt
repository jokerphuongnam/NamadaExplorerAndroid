package com.monsjoker.namadaexplorer.uis.screens.validator_details.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun ValidatorDetailsHeaderStateView(
    dataState: DataState<Validator>,
    onRetry: (() -> Unit)? = null
) {
    when (dataState) {
        is DataState.Loading -> DataShimmerView()
        is DataState.Success -> DataView(dataState.data)
        is DataState.Error -> ErrorView(error = dataState.error, onRetry = onRetry)
    }
}

@Composable
private fun DataView(data: Validator) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Text(label = "Voting Power", value = (data.votingPower / 1_000_000).formattedWithCommas())
        Text(label = "Last Seen", value = data.height!!.formattedWithCommas())
        Text(label = "Public key", value = data.pubKey!!.uppercase())
        Spacer(modifier = Modifier.fillMaxHeight())
    }
}

@Composable
private fun DataShimmerView() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ComponentRectangleLine()
        ComponentRectangleLine()
        ComponentRectangleLineFullWidth()
        Row {
            Spacer(modifier = Modifier.width(50.dp))
            ComponentRectangleLineFullWidth()
        }
        Row {
            Spacer(modifier = Modifier.width(50.dp))
            ComponentRectangleLine()
        }
    }
}