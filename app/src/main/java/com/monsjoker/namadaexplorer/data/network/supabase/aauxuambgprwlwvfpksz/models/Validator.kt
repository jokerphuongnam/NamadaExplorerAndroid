package com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Validator(
    val height: Int?,
    val name: String?,
    val address: String?,
    @SerializedName("voting_power") val votingPower: Long,
    @SerializedName("pub_key") val pubKey: String?,
    @SerializedName("proposer_priority") val proposerPriority: Int?
) : Parcelable