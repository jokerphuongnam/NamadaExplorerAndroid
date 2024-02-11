package com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo

import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Block
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface TgwsikrpibxhbmtgrhboNetwork {
    @GET("blocks")
    suspend fun fetchBlocks(
        @Query("select") select: String,
        @Query("order") order: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<Block>
}