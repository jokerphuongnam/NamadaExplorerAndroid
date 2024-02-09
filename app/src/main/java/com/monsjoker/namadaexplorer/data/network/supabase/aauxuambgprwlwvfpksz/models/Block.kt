package com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Block(
    val height: Int,
    val hash: String,
    val time: String,
    @SerializedName("num_txs") val numTxs: Int,
    @SerializedName("proposer_address") val proposerAddress: String
) : Parcelable