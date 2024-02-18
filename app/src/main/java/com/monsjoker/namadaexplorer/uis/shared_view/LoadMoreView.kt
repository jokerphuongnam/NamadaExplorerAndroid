package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.runtime.Composable
import androidx.paging.LoadState

@Composable
fun LoadMoreView(loadMoreState: LoadState, retryAction: (() -> Unit)? = null) {
    when (loadMoreState) {
        LoadState.Loading -> {
            ProgressView()
        }

        is LoadState.NotLoading -> {

        }

        is LoadState.Error -> {
            val error = loadMoreState.error
            ErrorView(error = error) {
                retryAction?.invoke()
            }
        }
    }
}