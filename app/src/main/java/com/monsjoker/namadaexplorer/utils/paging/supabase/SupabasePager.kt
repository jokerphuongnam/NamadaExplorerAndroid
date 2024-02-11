package com.monsjoker.namadaexplorer.utils.paging.supabase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.monsjoker.namadaexplorer.utils.Constants

fun <Data : Any> supabasePager(loadData: suspend (page: Int) -> List<Data>): Pager<Int, Data> {
    return Pager(
        initialKey = 0,
        config = PagingConfig(
            pageSize = Constants.limitPage
        ),
        pagingSourceFactory = {
            SupabasePagingSource(
                loadData = loadData
            )
        }
    )
}