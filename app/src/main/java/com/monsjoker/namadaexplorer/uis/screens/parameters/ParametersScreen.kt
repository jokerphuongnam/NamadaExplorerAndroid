@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.parameters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.monsjoker.namadaexplorer.uis.screens.parameters.views.GenesisAccountsView
import com.monsjoker.namadaexplorer.uis.screens.parameters.views.ParametersDetailView
import com.monsjoker.namadaexplorer.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersScreen(
    navController: NavController,
    viewModel: ParametersViewModel = hiltViewModel()
) {
    val tomlDataState = viewModel.tomlDataState
    val genesisAccountsState = viewModel.genesisAccountsState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ParametersDetailView(dataState = tomlDataState) {
                    viewModel.loadTomlData()
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text(
                        text = "Genesis Validators",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Chain ID: ${Constants.chainID}"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    this@LazyColumn.GenesisAccountsView(
                        genesisAccountsState = genesisAccountsState
                    )
                }
            }
        }
    }
}