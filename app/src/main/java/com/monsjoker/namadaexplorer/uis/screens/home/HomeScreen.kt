@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.R
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeState
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeBlocksView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeValidatorsView
import com.monsjoker.namadaexplorer.uis.shared_view.SelectedButton
import com.monsjoker.namadaexplorer.utils.Logger
import com.monsjoker.namadaexplorer.utils.visibility

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    onSelectedTab: (HomeState) -> Unit
) {
    val selectedState = rememberSaveable { mutableStateOf(HomeState.DETAILS) }
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
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            when (selectedState.value) {
                HomeState.DETAILS -> viewModel.loadHomeDetails()
                HomeState.VALIDATORS -> viewModel.load10Validators()
                HomeState.BLOCKS -> {
                    viewModel.isBlocksLoaded = false
                    viewModel.load10Blocks()
                }
            }
        }
    }

    LaunchedEffect(homeDetailsState.isLoading) {
        if (!homeDetailsState.isLoading) {
            pullToRefreshState.endRefresh()
        }
    }
    LaunchedEffect(validatorsState.isLoading) {
        if (!validatorsState.isLoading) {
            pullToRefreshState.endRefresh()
        }
    }
    LaunchedEffect(blocksState.isLoading) {
        if (!blocksState.isLoading) {
            pullToRefreshState.endRefresh()
        }
    }

    fun selectedTab(state: HomeState) {
        selectedState.value = state
        onSelectedTab(state)
        if (state == HomeState.VALIDATORS && !homeLoadedState.contains(HomeState.VALIDATORS)) {
            viewModel.load10Validators()
        }
        homeLoadedState.add(state)
    }

    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 8.dp
    ) {
        Box {
            Box(
                modifier = Modifier
                    .padding(top = 52.dp)
            ) {
                if (homeLoadedState.contains(HomeState.DETAILS)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .visibility(selectedState.value == HomeState.DETAILS)
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                HomeDetailsView(dataState = homeDetailsState) {
                                    viewModel.loadHomeDetails()
                                }
                            }

                            item {
                                SelectedButton(
                                    isSelected = false,
                                    iconId = R.drawable.ic_validators,
                                    title = "Top 10 Validators"
                                ) {
                                    selectedTab(HomeState.VALIDATORS)
                                }
                            }

                            item {
                                SelectedButton(
                                    isSelected = false,
                                    iconId = R.drawable.ic_block,
                                    title = "Top 10 Blocks"
                                ) {
                                    selectedTab(HomeState.BLOCKS)
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            PullToRefreshContainer(
                                state = pullToRefreshState,
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                if (homeLoadedState.contains(HomeState.VALIDATORS)) {
                    Box(
                        modifier = Modifier
                            .visibility(selectedState.value == HomeState.VALIDATORS)
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            PullToRefreshContainer(
                                state = pullToRefreshState,
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                if (homeLoadedState.contains(HomeState.BLOCKS)) {
                    Box(
                        modifier = Modifier
                            .visibility(selectedState.value == HomeState.BLOCKS)
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    ) {
                        HomeBlocksView(
                            dataState = blocksState,
                            navController = navController
                        ) {
                            viewModel.load10Blocks()
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            PullToRefreshContainer(
                                state = pullToRefreshState,
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedState.value.ordinal,
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
                        selected = selectedState.value == state,
                        onClick = {
                            selectedTab(state)
                        }
                    )
                }
            }
        }
    }
}