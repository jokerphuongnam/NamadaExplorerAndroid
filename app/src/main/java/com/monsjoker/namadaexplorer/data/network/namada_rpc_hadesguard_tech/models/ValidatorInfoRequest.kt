package com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.models

import com.google.gson.annotations.SerializedName

data class ValidatorInfoRequest(
    @SerializedName("id") val id: String,
    @SerializedName("method") val method: String,
    @SerializedName("params") val params: Params
) {
    data class Params(
        @SerializedName("path") val path: String,
        @SerializedName("data") val data: String,
        @SerializedName("prove") val prove: Boolean
    )

    companion object {
        val epoch = ValidatorInfoRequest(
            id = "65c28156-9a6b-49fa-989d-078c3c0a4cc3",
            method = "abci_query",
            params = Params(
                path = "/shell/epoch",
                data = "",
                prove = false
            )
        )

        val totalStake = ValidatorInfoRequest(
            id = "65c28156-9a6b-49fa-989d-078c3c0a4cc3",
            method = "abci_query",
            params = Params(
                path = "/vp/pos/total_stake",
                data = "",
                prove = false
            )
        )
    }
}