package com.monsjoker.namadaexplorer.uis.screens.blocks

import androidx.lifecycle.ViewModel
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseOrder
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseSelect
import com.monsjoker.namadaexplorer.data.network.supabase.models.createQueryString
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.paging.supabase.supabasePager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlocksViewModel @Inject constructor(private val supabaseTgNetwork: TgwsikrpibxhbmtgrhboNetwork) :
    ViewModel() {
    val pagingData = supabasePager { page ->
        supabaseTgNetwork.fetchBlocks(
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