package com.monsjoker.namadaexplorer.uis.screens.blocks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.domain.LoadMoreState
import com.monsjoker.namadaexplorer.data.domain.PrepareLoadMore
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Block
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseOrder
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseSelect
import com.monsjoker.namadaexplorer.data.network.supabase.models.createQueryString
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.paging.supabase.supabasePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlocksViewModel @Inject constructor(private val supabaseNetwork: TgwsikrpibxhbmtgrhboNetwork) :
    ViewModel() {
    val pagingData = supabasePager { page ->
        supabaseNetwork.fetchBlocks(
            select = SupabaseSelect.blocks.createQueryString(),
            order = listOf(
                SupabaseOrder(
                    field = SupabaseOrder.SortField.HEADER_HEIGHT,
                    order = SupabaseOrder.SortOrder.DESC
                )
            ).createQueryString(),
            limit = Constants.limitPage,
            offset = page * Constants.limitPage
        )
    }.flow
}