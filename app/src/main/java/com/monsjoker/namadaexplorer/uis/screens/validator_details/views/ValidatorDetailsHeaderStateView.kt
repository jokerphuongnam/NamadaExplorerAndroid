package com.monsjoker.namadaexplorer.uis.screens.validator_details.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.CardAction
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.utils.copyToClipboard
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
    val context = LocalContext.current

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardInfo(
                title = "Voting Power",
                value = (data.votingPower / 1_000_000).formattedWithCommas(),
                modifier = Modifier.weight(1f)
            )
            CardInfo(
                title = "Last Seen",
                value = data.height!!.formattedWithCommas(),
                modifier = Modifier.weight(1f)
            )
        }
        CardInfo(
            title = "Public key",
            value = data.pubKey!!.uppercase(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.fillMaxHeight())
    }
}

@Composable
private fun DataShimmerView() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardInfo(
                title = "Voting Power",
                modifier = Modifier.weight(1f)
            )
            CardInfo(
                title = "Last Seen",
                modifier = Modifier.weight(1f)
            )
        }
        CardInfo(
            title = "Public key",
            line = 3,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.fillMaxHeight())
    }
}