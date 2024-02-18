package com.monsjoker.namadaexplorer.uis.shared_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagingStateView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pagingItems: LazyPagingItems<T>,
    emptyText: String = "empty",
    loading: @Composable (() -> Unit),
    header: @Composable (() -> Unit)? = null,
    content: @Composable (index: Int, item: T) -> Unit
) {
    when (val dataState = pagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(contentPadding) then modifier
            ) {
                if (header != null) {
                    header()
                }

                loading.invoke()
            }
        }

        is LoadState.NotLoading -> {
            PagingListView(
                modifier = modifier,
                contentPadding = contentPadding,
                pagingItems = pagingItems,
                emptyText = emptyText,
                header = header,
                content = content
            )
        }

        is LoadState.Error -> {
            Column(modifier = Modifier.padding(contentPadding) then modifier) {
                if (header != null) {
                    header()
                }

                ErrorView(
                    error = dataState.error,
                    onRetry = {
                        pagingItems.refresh()
                    }
                )
            }
        }
    }
}

@Composable
private fun <T : Any> PagingListView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pagingItems: LazyPagingItems<T>,
    emptyText: String = "empty",
    header: @Composable (() -> Unit)? = null,
    content: @Composable (index: Int, item: T) -> Unit
) {
    pagingItems[0]
    if (pagingItems.itemCount == 0) {
        Column(modifier = Modifier.padding(contentPadding) then modifier) {
            if (header != null) {
                header()
            }

            Text(
                text = emptyText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    } else {
        Box(
            modifier = modifier
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = contentPadding
            ) {
                if (header != null) {
                    item {
                        header()
                    }
                }

                items(count = pagingItems.itemCount) { index ->
                    val item = pagingItems[index]
                    if (item != null) {
                        content(index, item)
                    }
                }

                item {
                    LoadMoreView(
                        loadMoreState = pagingItems.loadState.append,
                        retryAction = {
                            pagingItems.retry()
                        }
                    )
                }
            }
        }
    }
}