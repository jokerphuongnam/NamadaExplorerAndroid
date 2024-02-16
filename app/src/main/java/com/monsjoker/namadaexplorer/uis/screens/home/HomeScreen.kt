@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.blocks.views.BlockBottomSheetView
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeState
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeBlocksView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeValidatorsView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorBottomSheetView

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val selectedTab = remember { mutableStateOf(HomeState.DETAILS) }
    val homeDetailsState = viewModel.homeDetailsState
    val validatorsState = viewModel.validatorsState
    val blocksState = viewModel.blocksState
    val sheetState = rememberModalBottomSheetState()
    var validatorSelected by rememberSaveable {
        mutableStateOf<Validator?>(null)
    }
    var blockSelected by rememberSaveable {
        mutableStateOf<Block?>(null)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = "Home")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                TabRow(selectedTabIndex = selectedTab.value.ordinal) {
                    HomeState.entries.forEach { state ->
                        Tab(
                            text = {
                                Text(
                                    text = state.rawValue
                                )
                            },
                            selected = selectedTab.value == state,
                            onClick = {
                                selectedTab.value = state
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTab.value) {
                    HomeState.DETAILS -> {
                        HomeDetailsView(dataState = homeDetailsState) {
                            viewModel.loadHomeDetails()
                        }
                    }

                    HomeState.VALIDATORS -> {
                        HomeValidatorsView(dataState = validatorsState,
                            itemClickable = { validator ->
                                validatorSelected = validator
                            }
                        ) {
                            viewModel.load10Validators()
                        }
                    }

                    HomeState.BLOCKS -> {
                        HomeBlocksView(
                            dataState = blocksState, itemClickable = { block ->
                                blockSelected = block
                            }
                        ) {
                            viewModel.load10Blocks()
                        }
                    }
                }
            }
        }
    }


    if (validatorSelected != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                validatorSelected = null
            }
        ) {
            ValidatorBottomSheetView(
                navController = navController,
                validator = validatorSelected!!
            ) {
                validatorSelected = null
            }
        }
    }

    if (blockSelected != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                blockSelected = null
            }
        ) {
            BlockBottomSheetView(
                navController = navController,
                block = blockSelected!!
            ) {
                blockSelected = null
            }
        }
    }
}