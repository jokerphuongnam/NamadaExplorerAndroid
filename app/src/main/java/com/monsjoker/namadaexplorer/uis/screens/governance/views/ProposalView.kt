package com.monsjoker.namadaexplorer.uis.screens.governance.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import com.monsjoker.namadaexplorer.utils.formattedWithCommas


@Composable
fun ProposalView(index: Int, proposal: Proposal, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(Color.Yellow)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) then modifier
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = index.formattedWithCommas(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = proposal.kind,
                        fontSize = 12.sp
                    )

                    Text(
                        text = proposal.result.value,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${proposal.graceEpoch}: ${proposal.startEpoch}/${proposal.endEpoch}",
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = proposal.author.account,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    Column {
                        Text(
                            text = "Votes",
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            @Composable
                            fun VotingView(title: String, voting: String) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = title,
                                            fontWeight = FontWeight.Bold
                                        )

                                        val votingNumber = voting.toDouble() / 1_000
                                        Text(text = votingNumber.toInt().toString())
                                    }
                                }
                            }

                            VotingView(title = "Abstain", voting = proposal.abstainVotes)
                            VotingView(title = "Nay", voting = proposal.nayVotes)
                            VotingView(title = "Yay", voting = proposal.yayVotes)
                        }
                    }
                }
            }

            Text(
                text = proposal.content
            )
        }
    }
}