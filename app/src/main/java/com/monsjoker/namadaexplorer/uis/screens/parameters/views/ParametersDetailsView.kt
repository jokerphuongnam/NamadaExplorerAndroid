package com.monsjoker.namadaexplorer.uis.screens.parameters.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.local.models.TomlData
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.stringDate
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@Composable
fun ParametersDetailView(dataState: DataState<TomlData>, onRetry: (() -> Unit)? = null) {
    when (dataState) {
        is DataState.Loading -> Box {}
        is DataState.Success -> DataView(dataState.data)
        is DataState.Error -> ErrorView(error = dataState.error, onRetry = onRetry)
    }
}

@Composable
private fun DataView(data: TomlData) {
    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Section(sectionTitle = "Genesis Parameters") {
            Text(label = "Genesis Time", value = Date().stringDate)
            Text(label = "Chain ID", value = Constants.chainID)
        }

        Section(sectionTitle = "Chain Parameters") {
            Text(label = "Max Transaction Bytes", value = data.parameters.maxTxBytes!!)
            Text(label = "Native Token", value = data.parameters.nativeToken)
            Text(label = "Min Number of Blocks", value = data.parameters.minNumOfBlocks!!)
            Text(
                label = "Max Expected Time per Block",
                value = data.parameters.maxExpectedTimePerBlock!!
            )
            Text(label = "Max Proposal Bytes", value = data.parameters.maxProposalBytes!!)
            Text(label = "Epochs per Year", value = data.parameters.epochsPerYear!!)
            Text(
                label = "Max Signatures per Transaction",
                value = data.parameters.maxSignaturesPerTransaction!!
            )
            Text(label = "Max Block Gas", value = data.parameters.maxBlockGas!!)
            Text(
                label = "Fee Unshielding Descriptions Limit",
                value = data.parameters.feeUnshieldingGasLimit!!
            )
        }

        Section(sectionTitle = "Proof of Stake Parameters") {
            Text(label = "Max Validator Slots", value = data.posParams.maxValidatorSlots!!)
            Text(label = "Pipeline Length", value = data.posParams.pipelineLen!!)
            Text(label = "Unbonding Length", value = data.posParams.unbondingLen!!)
            Text(label = "TM Votes Per Token", value = data.posParams.tmVotesPerToken!!)
            Text(label = "Block Proposer Reward", value = data.posParams.blockProposerReward!!)
            Text(label = "Block Vote Reward", value = data.posParams.blockVoteReward!!)
            Text(label = "Max Inflation Rate", value = data.posParams.maxInflationRate!!)
            Text(label = "Target Staked Ratio", value = data.posParams.targetStakedRatio!!)
            Text(
                label = "Duplicate Vote Min Slash Rate",
                value = data.posParams.duplicateVoteMinSlashRate!!
            )
            Text(
                label = "Light Client Attack Min Slash Rate",
                value = data.posParams.lightClientAttackMinSlashRate!!
            )
            Text(
                label = "Cubic Slashing Window Length",
                value = data.posParams.cubicSlashingWindowLength!!
            )
            Text(
                label = "Validator Stake Threshold",
                value = data.posParams.validatorStakeThreshold!!.toInt()
            )
            Text(label = "Liveness Window Check", value = data.posParams.livenessWindowCheck!!)
            Text(label = "Liveness Threshold", value = data.posParams.livenessThreshold!!)
            Text(label = "Rewards Gain P", value = data.posParams.rewardsGainP!!)
            Text(label = "Rewards Gain D", value = data.posParams.rewardsGainD!!)
        }

        Section(sectionTitle = "Governance Parameters") {
            Text(label = "Min Proposal Fund", value = data.govParams.minProposalFund!!)
            Text(label = "Max Proposal Code Size", value = data.govParams.maxProposalCodeSize!!)
            Text(
                label = "Min Proposal Voting Period",
                value = data.govParams.minProposalVotingPeriod!!
            )
            Text(label = "Max Proposal Period", value = data.govParams.maxProposalPeriod!!)
            Text(
                label = "Max Proposal Content Size",
                value = data.govParams.maxProposalContentSize!!
            )
            Text(
                label = "Min Proposal Grace Epochs",
                value = data.govParams.minProposalGraceEpochs!!
            )
        }
    }
}

@Composable
private fun Section(sectionTitle: String, content: @Composable ColumnScope.() -> Unit) {
    var isShowContent by rememberSaveable {
        mutableStateOf(true)
    }
    val rotation = smoothRotation(if (isShowContent) 0f else 180f)
    val animatedRotation by animateFloatAsState(
        targetValue = rotation.value,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearOutSlowInEasing
        ),
        label = "FloatAnimation"
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                onClick = {
                    isShowContent = !isShowContent
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = sectionTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .weight(1f)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_up_arrow),
                        contentDescription = "Up",
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(animatedRotation)
                    )
                }
            }

            AnimatedVisibility(
                visible = isShowContent,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    content = content
                )
            }
        }
    }
}

@Composable
private fun smoothRotation(rotation: Float): MutableState<Float> {
    val storedRotation = remember { mutableStateOf(rotation) }

    LaunchedEffect(rotation) {
        snapshotFlow { rotation }
            .collectLatest { newRotation ->
                val diff = newRotation - storedRotation.value
                val shortestDiff = when {
                    diff > 180 -> diff - 360
                    diff < -180 -> diff + 360
                    else -> diff
                }
                storedRotation.value = storedRotation.value + shortestDiff
            }
    }

    return storedRotation
}