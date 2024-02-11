package com.monsjoker.namadaexplorer.uis.screens.transactions

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
class TransactionsViewModel @Inject constructor(
    private val supabaseTgNetwork: TgwsikrpibxhbmtgrhboNetwork
) : ViewModel() {
    val transfersPagingData = supabasePager { page ->
        supabaseTgNetwork.fetchTransfers(
            select = SupabaseSelect.empty.createQueryString(),
            limit = Constants.limitPage,
            offset = page * Constants.limitPage
        )
    }.flow

    val bondsPagingData = supabasePager { page ->
        supabaseTgNetwork.fetchBonds(
            select = SupabaseSelect.empty.createQueryString(),
            limit = Constants.limitPage,
            offset = page * Constants.limitPage
        )
    }.flow
}