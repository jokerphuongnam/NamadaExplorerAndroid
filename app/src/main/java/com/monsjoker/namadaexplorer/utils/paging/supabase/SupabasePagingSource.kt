package com.monsjoker.namadaexplorer.utils.paging.supabase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.monsjoker.namadaexplorer.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabasePagingSource<Data : Any>(
    private val loadData: suspend (page: Int) -> List<Data>
) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            withContext(Dispatchers.IO) {
                val page = params.key ?: 0
                val responses = loadData(page)
                LoadResult.Page(
                    data = responses,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (responses.isEmpty()) null else page + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

