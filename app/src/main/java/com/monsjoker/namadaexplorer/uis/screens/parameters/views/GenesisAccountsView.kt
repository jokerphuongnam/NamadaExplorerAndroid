@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.parameters.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.format
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun LazyListScope.GenesisAccountsView(
    genesisAccountsState: DataState<List<GenesisAccount>>,
    onRetry: (() -> Unit)? = null
) {
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        when (genesisAccountsState) {
            is DataState.Loading -> {
                ProgressView()
            }

            is DataState.Success -> {
                val genesisAccounts = genesisAccountsState.data

                if (genesisAccounts.isEmpty()) {
                    Text(
                        text = "Validator is empty",
                        style = typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(genesisAccounts) { index, genesisAccount ->
                            GenesisAccountView(index + 1, genesisAccount)
                        }
                    }
                }
            }

            is DataState.Error -> {
                val error = genesisAccountsState.error
                ErrorView(error = error, onRetry = onRetry)
            }
        }
    }
}

@Composable
private fun GenesisAccountView(
    index: Int,
    genesisAccount: GenesisAccount,
) {
    val sheetState = rememberModalBottomSheetState()
    var isShowBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    val typography = MaterialTheme.typography
    Row(
        modifier = Modifier
            .clickable {
                isShowBottomSheet = true
            }
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(Color.Yellow)
            .padding(vertical = 8.dp)
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row {
                Text(
                    text = genesisAccount.alias,
                    style = typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = genesisAccount.netAddress,
                    style = typography.bodyMedium,
                )
            }
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = genesisAccount.bondAmount.toDouble().formattedWithCommas(),
                        style = typography.bodyMedium
                    )
                    val commissionRate = genesisAccount.commissionRate.toDoubleOrNull()
                    val maxCommissionRateChange =
                        genesisAccount.maxCommissionRateChange.toDoubleOrNull()
                    if (commissionRate != null && maxCommissionRateChange != null) {
                        Text(
                            text = "${(commissionRate * 100).format(2)}% / ${
                                (maxCommissionRateChange * 100).format(
                                    2
                                )
                            }%",
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    if (isShowBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isShowBottomSheet = false
            }
        ) {
            GenesisAccountDetailsBottomSheetView(
                genesisAccount = genesisAccount,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun GenesisAccountDetailsBottomSheetView(
    genesisAccount: GenesisAccount,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = genesisAccount.alias,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            val commissionRate = genesisAccount.commissionRate.toDouble()
            val maxCommissionRateChange =
                genesisAccount.maxCommissionRateChange.toDouble()
            Text(
                label = "Commission rate",
                value = "${(commissionRate * 100).format(2)}%"
            )
            Text(
                label = "Max commission rate change",
                value = "${(maxCommissionRateChange * 100).format(2)}%"
            )
            Text(
                label = "Net address",
                value = genesisAccount.netAddress
            )
            Text(
                label = "Bound",
                value = genesisAccount.bondAmount.toDouble()
            )
            Text(
                label = "Consensus key",
                value = genesisAccount.consensusKeyPk.uppercase()
            )
            Text(
                label = "Hashed key",
                value = genesisAccount.hashedKey
            )
            Text(
                label = "Address",
                value = genesisAccount.address.uppercase()
            )
        }
    }
}