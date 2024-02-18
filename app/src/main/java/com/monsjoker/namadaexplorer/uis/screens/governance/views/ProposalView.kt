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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.monsjoker.namadaexplorer.uis.shared_view.BottomSheetSelectedView
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLineFullWidth
import com.monsjoker.namadaexplorer.uis.shared_view.ComponentRectangleLine
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressBarMultipleValue
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressBarValue
import com.monsjoker.namadaexplorer.uis.shared_view.Text
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
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = {
            isShowBottomSheet = true
        },
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
                            text = proposal.id.toString(),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            modifier = Modifier.width(24.dp)
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
                                            value = proposal.yayVotes.toFloat(),
                                            color = Color.Green
                                        ),
                                        ProgressBarValue(
                                            value = proposal.nayVotes.toFloat(),
                                            color = Color.Red
                                        ),
                                        ProgressBarValue(
                                            value = proposal.yayVotes.toFloat(),
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
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = proposal.id.toString(),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Text(label = "Author", value = proposal.author.account)
            }
            item {
                Text(label = "Kind", value = proposal.kind)
            }
            item {
                Text(label = "Result", value = proposal.resultEnum?.value ?: proposal.result)
            }
            item {
                Text(label = "Start epoch", value = proposal.startEpoch)
            }
            item {
                Text(label = "End epoch", value = proposal.endEpoch)
            }
            item {
                Text(label = "Grace epoch", value = proposal.graceEpoch)
            }
            item {
                Text(label = "Yes votes", value = proposal.yayVotes)
            }
            item {
                Text(label = "No votes", value = proposal.nayVotes)
            }
            item {
                Text(label = "Abstain votes", value = proposal.abstainVotes)
            }
            item {
                BottomSheetSelectedView(
                    title = "Content",
                    text = proposal.content,
                    isBoolContent = false
                )
            }
        }
    }
}

@Composable
fun ProposalShimmerView() {
    Card(
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
                .padding(horizontal = 12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp ),
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