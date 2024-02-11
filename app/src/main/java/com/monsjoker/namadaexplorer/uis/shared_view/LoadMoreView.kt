package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.runtime.Composable
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.monsjoker.namadaexplorer.data.domain.LoadMoreState

//@Composable
//fun LoadMoreView(loadMoreState: LoadMoreState, retryAction: (() -> Unit)?) {
//    when (loadMoreState) {
//        LoadMoreState.Loading -> {
//            ProgressView()
//        }
//
//        is LoadMoreState.Error -> {
//            val error = loadMoreState.error
//            ErrorView(error = error) {
//                retryAction?.invoke()
//            }
//        }
//    }
//}

@Composable
fun LoadMoreView(loadMoreState: LoadState, retryAction: (() -> Unit)? = null) {
    when(loadMoreState) {
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