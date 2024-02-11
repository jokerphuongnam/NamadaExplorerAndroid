package com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bond (
    @SerializedName("tx_id")
    val txID: String,
    val validator: String,
    val amount: String,
    val source: String,
    val bond: Boolean
) : Parcelable