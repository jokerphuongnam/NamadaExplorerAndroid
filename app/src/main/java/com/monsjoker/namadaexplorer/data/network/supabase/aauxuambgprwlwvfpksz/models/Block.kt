package com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Block(
    @SerializedName("block_id")
    val blockID: String,
    @SerializedName("header_height")
    val headerHeight: Long,
    @SerializedName("header_time")
    val headerTime: String,
    @SerializedName("header_proposer_address")
    val headerProposerAddress: String
) : Parcelable