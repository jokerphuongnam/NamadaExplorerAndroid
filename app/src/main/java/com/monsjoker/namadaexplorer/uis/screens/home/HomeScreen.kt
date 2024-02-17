@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeState
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeBlocksView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeValidatorsView

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val selectedTab = rememberSaveable { mutableStateOf(HomeState.DETAILS) }
    val homeDetailsState = viewModel.homeDetailsState
    val validatorsState = viewModel.validatorsState
    val blocksState = viewModel.blocksState
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
        ) {
            when (selectedTab.value) {
                HomeState.DETAILS -> {
                    HomeDetailsView(dataState = homeDetailsState) {
                        viewModel.loadHomeDetails()
                    }
                }

                HomeState.VALIDATORS -> {
                    HomeValidatorsView(dataState = validatorsState,
                        itemClickable = { validator ->
                            navBackStackEntry?.savedStateHandle?.set(
                                "validator_address",
                                validator.address
                            )
                            navController.navigate("validator")
                        }
                    ) {
                        viewModel.load10Validators()
                    }
                }

                HomeState.BLOCKS -> {
                    HomeBlocksView(
                        dataState = blocksState,
                        navController = navController
                    ) {
                        viewModel.load10Blocks()
                    }
                }
            }
        }
    }
}