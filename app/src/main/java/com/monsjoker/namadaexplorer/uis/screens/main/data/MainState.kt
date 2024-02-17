package com.monsjoker.namadaexplorer.uis.screens.main.data

import androidx.annotation.DrawableRes
import com.monsjoker.namadaexplorer.R

enum class MainState(val title: String, @DrawableRes val iconId: Int) {
    HOME("Home", R.drawable.ic_home),
    VALIDATORS("Validators", R.drawable.ic_validators),
    BLOCKS("Blocks", R.drawable.ic_block),
    TRANSACTIONS("Transactions", R.drawable.ic_transaction),
    GOVERNANCE("Governance", R.drawable.ic_governance),
    PARAMETERS("Parameters", R.drawable.ic_parameters);
}