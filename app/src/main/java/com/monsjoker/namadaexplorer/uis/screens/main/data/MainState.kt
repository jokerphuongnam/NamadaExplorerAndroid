package com.monsjoker.namadaexplorer.uis.screens.main.data

import androidx.annotation.DrawableRes
import com.monsjoker.namadaexplorer.R

enum class MainState(val route: String, val title: String, @DrawableRes val iconId: Int) {
    HOME("home", "Home", R.drawable.home_ic),
    VALIDATORS("validators", "Validators", R.drawable.validators_ic),
    BLOCKS("blocks", "Blocks", R.drawable.block_ic),
    TRANSACTIONS("transactions", "Transactions", R.drawable.transaction_ic),
    GOVERNANCE("governance", "Governance", R.drawable.governance_ic),
    PARAMETERS("parameters", "Parameters", R.drawable.parameters_ic);
}