package com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech

import com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.models.ValidatorInfoRequest
import com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.models.ValidatorsInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface NamadaRpcHadesGuardTechNetwork {
    @POST("/")
    suspend fun fetcVaidatorsInfo(@Body request: ValidatorInfoRequest): ValidatorsInfo
}