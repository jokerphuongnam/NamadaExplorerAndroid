package com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ValidatorsInfo(
    val jsonrpc: String,
    val id: String,
    val result: Result
) : Parcelable {
    @Parcelize
    data class Result(
        val response: Response
    ) : Parcelable

    @Parcelize
    data class Response(
        val value: String,
    ) : Parcelable
}