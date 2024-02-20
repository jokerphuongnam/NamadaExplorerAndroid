package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
import com.monsjoker.namadaexplorer.uis.shared_view.CircularChart
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRing
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.formattedWithCommas

@Composable
fun HomeDetailsView(dataState: DataState<HomeDetailsData>, onRetry: (() -> Unit)? = null) {
    when (dataState) {
        is DataState.Loading -> HomeDetailsShimmerLoadingData()
        is DataState.Success -> DataView(dataState.data)
        is DataState.Error -> {
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
                    ErrorView(error = dataState.error, onRetry = onRetry)
                }
            }
        }
    }
}

@Composable
private fun DataView(data: HomeDetailsData) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CardInfo(
            title = "Chain ID",
            value = Constants.chainID,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            shape = RoundedCornerShape(8.dp),
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    CircularChart(
                        value = ((data.totalStake.toFloat() / 1_000_000) / data.allState.toFloat()) * 100,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        backgroundCircleColor = Color.Transparent,
                        size = 100.dp,
                        thickness = 16.dp,
                        gapBetweenCircles = 24.dp
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Total Stake:",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            "${(data.totalStake.toDouble() / 1_000_000).formattedWithCommas()} NAAN",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Voting power:",
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            "${data.allState.formattedWithCommas()} NAAN",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Epoch",
                value = data.epoch,
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "lock Height",
                value = data.blockHeight,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Validators",
                value = data.validators,
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "Governance Proposals",
                value = data.governanceProposals,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun HomeDetailsShimmerLoadingData() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CardInfo(
            title = "Chain ID",
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            shape = RoundedCornerShape(8.dp),
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    ComponentRing(
                        size = 100.dp,
                        thickness = 16.dp,
                        gapBetweenCircles = 24.dp
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Total stake:",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                        ComponentRectangleLine(width = 200.dp)

                        Text(
                            text = "Voting power:",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                        ComponentRectangleLine(width = 200.dp)
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Epoch",
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "Block Height",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Validators",
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "Proposals",
                modifier = Modifier.weight(1f)
            )
        }
    }
}