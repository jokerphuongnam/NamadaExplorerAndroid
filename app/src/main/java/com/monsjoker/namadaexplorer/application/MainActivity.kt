package com.monsjoker.namadaexplorer.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.monsjoker.namadaexplorer.uis.screens.main.MainScreen
import com.monsjoker.namadaexplorer.uis.screens.validator_details.ValidatorDetailsView
import com.monsjoker.namadaexplorer.uis.theme.NamadaExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContent {
            NamadaExplorerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController = navController)
                    }
                    composable("validator") {
                        val validatorAddress =
                            navController.previousBackStackEntry?.savedStateHandle?.get<String>("validator_address")
                        if (validatorAddress != null) {
                            ValidatorDetailsView(
                                navController = navController,
                                validatorAddress = validatorAddress
                            )
                        }
                    }
                }
            }
        }
    }
}