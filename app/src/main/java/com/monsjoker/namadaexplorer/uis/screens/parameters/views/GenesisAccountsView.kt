@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.parameters.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.utils.format
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

fun LazyListScope.genesisAccountsView(
    genesisAccountsState: DataState<List<GenesisAccount>>,
    onRetry: (() -> Unit)? = null
) {
    when (genesisAccountsState) {
        is DataState.Loading -> {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (index in 0..9) {
                        GenesisAccountShimmerView()
                    }
                }
            }
        }

        is DataState.Success -> {
            val genesisAccounts = genesisAccountsState.data

            if (genesisAccounts.isEmpty()) {
                item {
                    Text(
                        text = "Validator is empty",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            } else {
                itemsIndexed(genesisAccounts) { index, genesisAccount ->
                    GenesisAccountView(index + 1, genesisAccount)
                }
            }
        }

        is DataState.Error -> {
            val error = genesisAccountsState.error
            item {
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

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isShowBottomSheet) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        onClick = {
            isShowBottomSheet = true
        },
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp),
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
    }

    if (isShowBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
            .background(MaterialTheme.colorScheme.secondaryContainer) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = genesisAccount.alias,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val commissionRate = genesisAccount.commissionRate.toDouble()
                    val maxCommissionRateChange =
                        genesisAccount.maxCommissionRateChange.toDouble()
                    CardInfo(
                        title = "Commission rate\n",
                        value = "${(commissionRate * 100).format(2)}%",
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "Max commission rate change",
                        value = "${(maxCommissionRateChange * 100).format(2)}%",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardInfo(
                        title = "Net address",
                        value = genesisAccount.netAddress,
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "Bound",
                        value = genesisAccount.bondAmount.toDouble(),
                        modifier = Modifier.weight(1f)
                    )
                }
                CardInfo(title = "Consensus key", value = genesisAccount.consensusKeyPk.uppercase())
                CardInfo(title = "Hashed key", value = genesisAccount.hashedKey)
                CardInfo(title = "Address", value = genesisAccount.address.uppercase())
            }
        }
    }
}

@Composable
private fun GenesisAccountShimmerView() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row {
                    ComponentRectangleLine(width = 100.dp)
                    Spacer(modifier = Modifier.weight(1f))
                    ComponentRectangleLine(width = 150.dp)
                }
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ComponentRectangleLine(width = 150.dp)
                        Spacer(modifier = Modifier.weight(1f))
                        ComponentRectangleLine(width = 100.dp)
                    }
                }
            }
        }
    }
}