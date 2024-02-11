package com.monsjoker.namadaexplorer.uis.screens.parameters.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.local.models.TomlData
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import com.monsjoker.namadaexplorer.uis.shared_view.Text
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.stringDate
import java.util.Date

@Composable
fun ParametersDetailView(dataState: DataState<TomlData>, onRetry: (() -> Unit)? = null) {
    when (dataState) {
        is DataState.Loading -> ProgressView()
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
            Text(label = "Max Signatures per Transaction", value = data.parameters.maxSignaturesPerTransaction!!)
            Text(label = "Max Block Gas", value = data.parameters.maxBlockGas!!)
            Text(label = "Fee Unshielding Descriptions Limit", value = data.parameters.feeUnshieldingGasLimit!!)
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
            Text(label = "Duplicate Vote Min Slash Rate", value = data.posParams.duplicateVoteMinSlashRate!!)
            Text(label = "Light Client Attack Min Slash Rate", value = data.posParams.lightClientAttackMinSlashRate!!)
            Text(label = "Cubic Slashing Window Length", value = data.posParams.cubicSlashingWindowLength!!)
            Text(label = "Validator Stake Threshold", value = data.posParams.validatorStakeThreshold!!)
            Text(label = "Liveness Window Check", value = data.posParams.livenessWindowCheck!!)
            Text(label = "Liveness Threshold", value = data.posParams.livenessThreshold!!)
            Text(label = "Rewards Gain P", value = data.posParams.rewardsGainP!!)
            Text(label = "Rewards Gain D", value = data.posParams.rewardsGainD!!)
        }

        Section(sectionTitle = "Governance Parameters") {
            Text(label = "Min Proposal Fund", value = data.govParams.minProposalFund!!)
            Text(label = "Max Proposal Code Size", value = data.govParams.maxProposalCodeSize!!)
            Text(label = "Min Proposal Voting Period", value = data.govParams.minProposalVotingPeriod!!)
            Text(label = "Max Proposal Period", value = data.govParams.maxProposalPeriod!!)
            Text(label = "Max Proposal Content Size", value = data.govParams.maxProposalContentSize!!)
            Text(label = "Min Proposal Grace Epochs", value = data.govParams.minProposalGraceEpochs!!)
        }
    }
}

@Composable
private fun Section(sectionTitle: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = sectionTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            content = content
        )
    }
}