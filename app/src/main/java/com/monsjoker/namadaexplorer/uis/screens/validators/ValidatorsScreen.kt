@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.monsjoker.namadaexplorer.uis.screens.validators

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.home.views.HomeDetailsView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorView
import com.monsjoker.namadaexplorer.uis.shared_view.PagingStateView

@Composable
fun ValidatorsScreen(
    navController: NavController,
    viewModel: ValidatorsViewModel = hiltViewModel()
) {
    val homeDetailsState = viewModel.homeDetailsState
    val pagingData = viewModel.pagingData.collectAsLazyPagingItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
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