package com.monsjoker.namadaexplorer.data.network.namada_info

import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import retrofit2.http.GET

interface NamadaInfoNetwork {
    @GET("shielded-expedition.88f17d1d14/output/genesis_accounts_min.json")
    suspend fun fetchGenesisAccounts(): List<GenesisAccount>
}