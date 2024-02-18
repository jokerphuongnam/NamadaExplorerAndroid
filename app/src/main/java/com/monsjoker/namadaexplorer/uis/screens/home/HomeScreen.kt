package com.monsjoker.namadaexplorer.uis.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeState
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeBlocksView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeValidatorsView
import com.monsjoker.namadaexplorer.utils.visibility

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel(), selectedTab: (HomeState) -> Unit) {
    val selectedTab = rememberSaveable { mutableStateOf(HomeState.DETAILS) }
    val homeDetailsState = viewModel.homeDetailsState
    val validatorsState = viewModel.validatorsState
    val blocksState = viewModel.blocksState
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val homeLoadedState by rememberSaveable {
        mutableStateOf(
            HashSet<HomeState>().also {
                it.add(HomeState.DETAILS)
            }
        )
    }

    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 8.dp
    ) {
        Column {
            TabRow(
                selectedTabIndex = selectedTab.value.ordinal,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .shadow(8.dp),
            ) {
                HomeState.entries.forEach { state ->
                    Tab(
                        text = {
                            Text(
                                text = state.rawValue,
                            )
                        },
                        selected = selectedTab.value == state,
                        onClick = {
                            selectedTab.value = state
                            selectedTab(state)
                            if (state == HomeState.VALIDATORS && !homeLoadedState.contains(HomeState.VALIDATORS)) {
                                viewModel.load10Validators()
                            }
                            homeLoadedState.add(state)
                        }
                    )
                }
            }

            Box(modifier = Modifier) {
                if (homeLoadedState.contains(HomeState.DETAILS)) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .visibility(selectedTab.value == HomeState.DETAILS)
                            .fillMaxWidth()
                    ) {
                        HomeDetailsView(dataState = homeDetailsState) {
                            viewModel.loadHomeDetails()
                        }

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                if (homeLoadedState.contains(HomeState.VALIDATORS)) {
                    Box(
                        modifier = Modifier
                            .visibility(selectedTab.value == HomeState.VALIDATORS)
                    ) {
                        HomeValidatorsView(
                            dataState = validatorsState,
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
                }

                if (homeLoadedState.contains(HomeState.BLOCKS)) {
                    Box(
                        modifier = Modifier
                            .visibility(selectedTab.value == HomeState.BLOCKS)
                    ) {
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
}