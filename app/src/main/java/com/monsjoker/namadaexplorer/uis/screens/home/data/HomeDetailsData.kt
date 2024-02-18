package com.monsjoker.namadaexplorer.uis.screens.home.data

data class HomeDetailsData(
    val epoch: Long,
    val blockHeight: Long,
    val totalStake: Long,
    val allState: Long,
    val validators: Int,
    val governanceProposals: Int
)