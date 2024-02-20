@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.governance.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import com.monsjoker.namadaexplorer.uis.shared_view.CardInfo
import com.monsjoker.namadaexplorer.uis.shared_view.ChartConfig
import com.monsjoker.namadaexplorer.uis.shared_view.CircularChart
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressBarMultipleValue
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressBarValue
import com.monsjoker.namadaexplorer.uis.theme.DarkGreen
import com.monsjoker.namadaexplorer.uis.theme.DarkRed
import com.monsjoker.namadaexplorer.uis.theme.DarkYellow
import com.monsjoker.namadaexplorer.uis.theme.LightGreen
import com.monsjoker.namadaexplorer.uis.theme.LightRed
import com.monsjoker.namadaexplorer.uis.theme.LightYellow

@Composable
fun ProposalView(index: Int, proposal: Proposal, modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState()
    var isShowBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

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
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = when (proposal.resultEnum) {
                                        Proposal.Result.Pending -> Color.LightYellow
                                        Proposal.Result.VotingPeriod -> Color.LightGreen
                                        Proposal.Result.Rejected -> Color.LightRed
                                        else -> Color.Transparent
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            Text(
                                text = proposal.resultEnum?.value ?: proposal.result,
                                fontSize = 12.sp,
                                color = when (proposal.resultEnum) {
                                    Proposal.Result.Pending -> Color.DarkYellow
                                    Proposal.Result.VotingPeriod -> Color.DarkGreen
                                    Proposal.Result.Rejected -> Color.DarkRed
                                    else -> Color.Black
                                },

                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = proposal.kind,
                            fontSize = 12.sp
                        )
                    }
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${proposal.id}     ".take(5),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(2.5.dp))
                        ) {
                            if (proposal.resultEnum == Proposal.Result.Pending) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxSize()
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(2.5.dp),
                                            color = Color.Black
                                        )
                                )
                            } else {
                                ProgressBarMultipleValue(
                                    values = listOf(
                                        ProgressBarValue(
                                            value = proposal.yayVotes.toLong(),
                                            color = Color.Green
                                        ),
                                        ProgressBarValue(
                                            value = proposal.nayVotes.toLong(),
                                            color = Color.Red
                                        ),
                                        ProgressBarValue(
                                            value = proposal.abstainVotes.toLong(),
                                            color = Color.Gray
                                        ),
                                    )
                                )
                            }
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
            ProposalDetailsBottomSheetView(
                proposal = proposal,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun ProposalDetailsBottomSheetView(proposal: Proposal, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Proposal details",
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
                if (proposal.resultEnum != Proposal.Result.Pending) {
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
                                text = "Votes",
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularChart(
                                    configs = listOf(
                                        ChartConfig(
                                            value = proposal.yayVotes.toLong(),
                                            color = Color.Green,
                                            legend = "Yes votes",
                                            unit = "Votes"
                                        ),
                                        ChartConfig(
                                            value = proposal.nayVotes.toLong(),
                                            color = Color.Red,
                                            legend = "Nah votes",
                                            unit = "Votes"
                                        ),
                                        ChartConfig(
                                            value = proposal.abstainVotes.toLong(),
                                            color = Color.Gray,
                                            legend = "Abstain votes",
                                            unit = "Votes"
                                        )
                                    ),
                                    size = 100.dp,
                                    thickness = 16.dp,
                                    gapBetweenCircles = 24.dp
                                )
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardInfo(
                        title = "ID",
                        value = proposal.id.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "Kind",
                        value = proposal.kind,
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "Result",
                        value = proposal.resultEnum?.value ?: proposal.result,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardInfo(
                        title = "Start epoch",
                        value = proposal.startEpoch,
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(
                        title = "End epoch",
                        value = proposal.endEpoch,
                        modifier = Modifier.weight(1f)
                    )
                }

                CardInfo(
                    title = proposal.author.account,
                    value = proposal.content,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ProposalShimmerView() {
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(horizontal = 12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ComponentRectangleLine()

                    Spacer(modifier = Modifier.weight(1f))

                    ComponentRectangleLine()
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ComponentRectangleLine(width = 50.dp)

                    ComponentRectangleLineFullWidth(height = 5.dp)
                }
            }
        }
    }
}