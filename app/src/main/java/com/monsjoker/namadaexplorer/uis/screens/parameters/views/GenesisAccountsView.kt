package com.monsjoker.namadaexplorer.uis.screens.parameters.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import com.monsjoker.namadaexplorer.utils.format

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
    val typography = MaterialTheme.typography
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(Color.Yellow)
            .padding(vertical = 8.dp)
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = index.toString(),
            style = typography.bodyLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = genesisAccount.alias,
                style = typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Text(
                text = genesisAccount.netAddress,
                style = typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = genesisAccount.hashedKey,
                    style = typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = genesisAccount.address,
                    style = typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = genesisAccount.bondAmount,
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