package com.monsjoker.namadaexplorer.data.network.id_namada_red.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProposalModels(
    val proposals: List<Proposal>
) : Parcelable

@Parcelize
data class Proposal(
    val id: Long,
    val content: String,
    val kind: String,
    val author: Author,
    @SerializedName("start_epoch")
    val startEpoch: Long,
    @SerializedName("end_epoch")
    val endEpoch: Long,
    @SerializedName("grace_epoch")
    val graceEpoch: Long,
    val result: Result,
    @SerializedName("yay_votes")
    val yayVotes: String,
    @SerializedName("nay_votes")
    val nayVotes: String,
    @SerializedName("abstain_votes")
    val abstainVotes: String
) : Parcelable {
    @Parcelize
    data class Author(
        @SerializedName("Account")
        val account: String
    ) : Parcelable

    @Parcelize
    enum class Result(val value: String) : Parcelable {
        Pending("Pending"),
        VotingPeriod("VotingPeriod"),
        Rejected("Rejected");
    }
}