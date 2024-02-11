package com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz

import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import retrofit2.http.GET
import retrofit2.http.Query

interface AauxuambgprwlwvfpkszNetwork {
    @GET("validators")
    suspend fun fetchValidators(
        @Query("select") select: String,
        @Query("order") order: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<Validator>

    @GET("validators")
    suspend fun fetchValidator(
        @Query("select") select: String,
        @Query("address") address: String
    ): List<Validator>
}