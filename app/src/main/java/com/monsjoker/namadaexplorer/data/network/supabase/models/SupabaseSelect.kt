package com.monsjoker.namadaexplorer.data.network.supabase.models

enum class SupabaseSelect(val rawValue: String) {
    HEIGHT("height"),
    HASH("hash"),
    TIME("time"),
    NUM_TXS("num_txs"),
    PROPOSER_ADDRESS("proposer_address"),
    VOTING_POWER("voting_power");

    companion object {
        val all: List<SupabaseSelect> = entries
        val empty: List<SupabaseSelect> = listOf()
    }
}

fun List<SupabaseSelect>.createQueryString(): String {
    if (isEmpty()) return "*"
    val propertyNames = map { it.rawValue }
    val encodedProperties = propertyNames.map { it.replace(" ", "%20") }
    return encodedProperties.joinToString(separator = ",")
}