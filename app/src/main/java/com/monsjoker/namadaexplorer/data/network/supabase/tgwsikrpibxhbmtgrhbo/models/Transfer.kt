package com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transfer (
    @SerializedName("tx_id")
    val txID: String,
    val source: String,
    val target: String,
    val token: String,
    val amount: String,
    val key: String? = null,
    val shielded: String? = null
) : Parcelable
