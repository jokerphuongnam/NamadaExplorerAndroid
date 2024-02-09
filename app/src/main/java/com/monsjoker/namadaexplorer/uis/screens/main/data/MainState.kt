package com.monsjoker.namadaexplorer.uis.screens.main.data

enum class MainState(val route: String, val title: String) {
    HOME("home", "Home"),
    VALIDATORS("validators", "Validators"),
    BLOCKS("blocks", "Blocks"),
    TRANSACTIONS("transactions", "Transactions"),
    GOVERNANCE("governance", "Governance"),
    PARAMETERS("parameters", "Parameters");
}