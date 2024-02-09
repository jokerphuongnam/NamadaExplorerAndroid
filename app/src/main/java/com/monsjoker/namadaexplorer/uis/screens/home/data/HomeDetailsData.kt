package com.monsjoker.namadaexplorer.uis.screens.home.data

data class HomeDetailsData(
    val epoch: Int = 0,
    val blockHeight: Int,
    val totalStake: Int,
    val validators: Int,
    val governanceProposals: Int = 0
)