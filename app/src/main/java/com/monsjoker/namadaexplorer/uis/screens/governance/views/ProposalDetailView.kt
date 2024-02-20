package com.monsjoker.namadaexplorer.uis.screens.governance.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
import com.monsjoker.namadaexplorer.uis.shared_view.ChartConfig
import com.monsjoker.namadaexplorer.uis.shared_view.CircularChart
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRing
import com.monsjoker.namadaexplorer.utils.colorByHashCode
import com.monsjoker.namadaexplorer.utils.insertSpacesBeforeUppercase

@Composable
fun ProposalDetailStateView(
    dataState: DataState<List<Proposal>>
) {
    when (dataState) {
        is DataState.Loading -> ProposalDetailShimmerView()
        is DataState.Success -> ProposalDetailView(data = dataState.data)
        else -> {}
    }
}

@Composable
private fun ProposalDetailView(data: List<Proposal>) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Proposals",
                value = data.size,
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "Largest ID",
                value = data.maxOf {
                    it.id
                },
                modifier = Modifier.weight(1f)
            )
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Status",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularChart(
                        configs = data.map { it.result }.toHashSet().map { result ->
                            ChartConfig(
                                value = data.count { it.result == result },
                                color = when (Proposal.Result.fromValue(value = result)) {
                                    Proposal.Result.Pending -> Color.Yellow
                                    Proposal.Result.VotingPeriod -> Color.Green
                                    Proposal.Result.Rejected -> Color.Red
                                    null -> result.colorByHashCode
                                },
                                legend = result.insertSpacesBeforeUppercase()
                            )
                        },
                        size = 100.dp,
                        thickness = 16.dp,
                        gapBetweenCircles = 24.dp
                    )
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Kind",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularChart(
                        configs = data.map { it.kind }.toHashSet().map { kind ->
                            ChartConfig(
                                value = data.count { it.kind == kind },
                                color = kind.colorByHashCode,
                                legend = kind.insertSpacesBeforeUppercase()
                            )
                        },
                        size = 100.dp,
                        thickness = 16.dp,
                        gapBetweenCircles = 24.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun ProposalDetailShimmerView() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardInfo(
                title = "Proposals",
                modifier = Modifier.weight(1f)
            )

            CardInfo(
                title = "Largest ID",
                modifier = Modifier.weight(1f)
            )
        }

        for (sectionTile in listOf("Status", "Kind")) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = sectionTile,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ComponentRing(
                            size = 100.dp,
                            thickness = 16.dp,
                            gapBetweenCircles = 24.dp
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ComponentRectangleLine(width = 200.dp)
                            ComponentRectangleLine(width = 40.dp)
                        }
                    }
                }
            }
        }
    }
}