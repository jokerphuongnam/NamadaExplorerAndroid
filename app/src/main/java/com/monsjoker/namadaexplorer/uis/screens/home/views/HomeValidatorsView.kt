package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView
import com.monsjoker.namadaexplorer.uis.shared_view.ProgressView
import com.monsjoker.namadaexplorer.uis.shared_view.ValidatorView
import com.monsjoker.namadaexplorer.utils.Logger

@Composable
fun HomeValidatorsView(
    dataState: DataState<List<Validator>>,
    retryAction: (() -> Unit)? = null
) {
    Logger.i("HomeValidatorsView", dataState)
    val paddingValues = PaddingValues(
        top = 32.dp,
        bottom = 52.dp + 8.dp
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            when (dataState) {
                is DataState.Loading -> {
                    ProgressView()
                }

                is DataState.Success -> {
                    val validators = dataState.data.toList()

                    if (validators.isEmpty()) {
                        Text(
                            text = "Validator is empty",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(paddingValues)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(
                                items = validators,
                                key = { index, validator -> index }
                            ) { index, validator ->
                                ValidatorView(
                                    index = index + 1,
                                    validator = validator
                                )
                            }
                        }
                    }
                }

                is DataState.Error -> {
                    ErrorView(
                        error = dataState.error,
                        onRetry = retryAction
                    )
                }
            }
        }
    }
}