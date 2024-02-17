package com.monsjoker.namadaexplorer.uis.screens.main.data

import androidx.annotation.DrawableRes
import com.monsjoker.namadaexplorer.R

enum class MainState(val route: String, val title: String, @DrawableRes val iconId: Int) {
    HOME("home", "Home", R.drawable.ic_home),
    VALIDATORS("validators", "Validators", R.drawable.ic_validators),
    BLOCKS("blocks", "Blocks", R.drawable.ic_block),
    TRANSACTIONS("transactions", "Transactions", R.drawable.ic_transaction),
    GOVERNANCE("governance", "Governance", R.drawable.ic_governance),
    PARAMETERS("parameters", "Parameters", R.drawable.ic_parameters);
}