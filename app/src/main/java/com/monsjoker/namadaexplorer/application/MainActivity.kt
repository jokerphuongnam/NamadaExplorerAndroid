package com.monsjoker.namadaexplorer.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.monsjoker.namadaexplorer.uis.screens.blocks.BlocksView
import com.monsjoker.namadaexplorer.uis.screens.home.HomeScreen
import com.monsjoker.namadaexplorer.uis.screens.main.MainScreen
import com.monsjoker.namadaexplorer.uis.screens.main.data.MainState
import com.monsjoker.namadaexplorer.uis.screens.parameters.ParametersView
import com.monsjoker.namadaexplorer.uis.screens.transactions.TransactionsView
import com.monsjoker.namadaexplorer.uis.screens.validators.ValidatorsView
import com.monsjoker.namadaexplorer.uis.theme.NamadaExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NamadaExplorerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController = navController)
                    }
                    composable(MainState.HOME.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(MainState.VALIDATORS.route) {
                        ValidatorsView(navController = navController)
                    }
                    composable(MainState.BLOCKS.route) {
                        BlocksView(navController = navController)
                    }
                    composable(MainState.TRANSACTIONS.route) {
                        TransactionsView(navController = navController)
                    }
                    composable(MainState.PARAMETERS.route) {
                        ParametersView(navController = navController)
                    }
                }
            }
        }
    }
}