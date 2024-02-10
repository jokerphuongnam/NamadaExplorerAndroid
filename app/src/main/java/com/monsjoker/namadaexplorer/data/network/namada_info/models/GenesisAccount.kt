package com.monsjoker.namadaexplorer.data.network.namada_info.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenesisAccount(
    @SerializedName("consensus_key_pk") val consensusKeyPk: String,
    @SerializedName("hashed_key") val hashedKey: String,
    val address: String,
    @SerializedName("commission_rate") val commissionRate: String,
    @SerializedName("max_commission_rate_change") val maxCommissionRateChange: String,
    val alias: String,
    @SerializedName("net_address") val netAddress: String,
    @SerializedName("bond_amount") val bondAmount: String
) : Parcelable
