@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.validator_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.uis.screens.validator_details.views.ValidatorDetailsHeaderStateView
import com.monsjoker.namadaexplorer.uis.screens.validator_details.views.validatorDetailBlocks

@Composable
fun ValidatorDetailsView(
    navController: NavController,
    viewModel: ValidatorDetailsViewModel = hiltViewModel(),
    validatorAddress: String
) {
    val validatorState = viewModel.validatorState
    val blocksState = viewModel.blocksState
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {
                viewModel.loadValidator(validatorAddress = validatorAddress)
                viewModel.loadBlocks(validatorAddress = validatorAddress)
            }

            Lifecycle.State.RESUMED -> {}
        }
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
                        Text(text = "Validator Details")
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
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = validatorAddress,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                item {
                    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                        ValidatorDetailsHeaderStateView(dataState = validatorState) {
                            viewModel.loadValidator(validatorAddress = validatorAddress)
                        }
                    }
                }

                item {
                    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Latest 10 Blocks from Validator")

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                validatorDetailBlocks(dataState = blocksState)
            }
        }
    }
}