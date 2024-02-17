@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.monsjoker.namadaexplorer.uis.screens.validators

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView

@Composable
fun ValidatorsView(navController: NavController, viewModel: ValidatorsViewModel = hiltViewModel()) {
    val homeDetailsState = viewModel.homeDetailsState
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Validators")
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PagingStateView(
                pagingItems = pagingData,
                emptyText = "Validator Empty",
                header = {
                    Box(modifier = Modifier.padding(vertical = 32.dp)) {
                        HomeDetailsView(
                            dataState = homeDetailsState,
                            onRetry = { viewModel.loadHomeDetails() },
                        )
                    }
                },
                content = { index, validator ->
                    ValidatorView(
                        index = index + 1,
                        validator = validator,
                        modifier = Modifier.clickable {
                            navBackStackEntry?.savedStateHandle?.set(
                                "validator_address",
                                validator.address
                            )
                            navController.navigate("validator")
                        }
                    )
                }
            )
        }
    }
}