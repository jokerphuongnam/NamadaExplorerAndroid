@file:OptIn(ExperimentalMaterial3Api::class)

package com.monsjoker.namadaexplorer.uis.screens.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.monsjoker.namadaexplorer.uis.screens.transactions.data.TransactionState
import com.monsjoker.namadaexplorer.uis.screens.transactions.views.BondsView
import com.monsjoker.namadaexplorer.uis.screens.transactions.views.TransfersView
import com.monsjoker.namadaexplorer.utils.visibility

@Composable
fun TransactionsScreen(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val selectedTab = remember { mutableStateOf(TransactionState.TRANSFERS) }
    val transfersPagingData = viewModel.transfersPagingData.collectAsLazyPagingItems()
    val bondsPagingData = viewModel.bondsPagingData.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
        ) {
            TabRow(
                selectedTabIndex = selectedTab.value.ordinal,
                modifier = Modifier.height(56.dp)
            ) {
                TransactionState.entries.forEach { state ->
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

            Box(modifier = Modifier.fillMaxSize()) {
                TransfersView(
                    pagingItems = transfersPagingData,
                    modifier = Modifier.visibility(selectedTab.value == TransactionState.TRANSFERS)
                )

                BondsView(
                    pagingItems = bondsPagingData,
                    modifier = Modifier.visibility(selectedTab.value == TransactionState.BONDS)
                )
            }
        }
    }
}