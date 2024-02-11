package com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo

import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Bond
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Transfer
import retrofit2.http.GET
import retrofit2.http.Query

interface TgwsikrpibxhbmtgrhboNetwork {
    @GET("blocks")
    suspend fun fetchBlocks(
        @Query("select") select: String,
        @Query("order") order: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("header_proposer_address") headerProposerAddress: String? = null
    ): List<Block>

    @GET("tx_transfer")
    suspend fun fetchTransfers(
        @Query("select") select: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<Transfer>

    @GET("tx_bond")
    suspend fun fetchBonds(
        @Query("select") select: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<Bond>
}