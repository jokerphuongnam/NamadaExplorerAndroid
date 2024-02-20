package com.monsjoker.namadaexplorer.uis.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
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
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorShimmerView
import com.monsjoker.namadaexplorer.uis.screens.validators.views.ValidatorView
import com.monsjoker.namadaexplorer.uis.shared_view.ErrorView

@Composable
fun HomeValidatorsView(
    dataState: DataState<List<Validator>>,
    itemClickable: ((Validator) -> Unit)? = null,
    retryAction: (() -> Unit)? = null
) {
    val paddingValues = PaddingValues(
        top = 32.dp,
        bottom = 52.dp + 8.dp
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            when (dataState) {
                is DataState.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 12.dp)
                    ) {
                        for (index in 0..9) {
                            ValidatorShimmerView()
                        }
                    }
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
                                .fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(
                                items = validators,
                                key = { index, _ -> index }
                            ) { index, validator ->
                                ValidatorView(
                                    index = index + 1,
                                    validator = validator,
                                    modifier = Modifier
                                ) {
                                    itemClickable?.invoke(validator)
                                }
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