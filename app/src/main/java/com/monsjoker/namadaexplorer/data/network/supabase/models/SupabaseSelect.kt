package com.monsjoker.namadaexplorer.data.network.supabase.models

enum class SupabaseSelect(val rawValue: String) {
    VOTING_POWER("voting_power"),

    BLOCK_ID("block_id"),
    HEADER_HEIGHT("header_height"),
    HEADER_TIME("header_time"),
    HEADER_PROPOSER_ADDRESS("header_proposer_address");
    companion object {
        val all: List<SupabaseSelect> = entries
        val empty: List<SupabaseSelect> = listOf()
        val blocks = listOf(BLOCK_ID, HEADER_HEIGHT, HEADER_TIME, HEADER_PROPOSER_ADDRESS)
    }
}

fun List<SupabaseSelect>.createQueryString(): String {
    if (isEmpty()) return "*"
    val propertyNames = map { it.rawValue }
    val encodedProperties = propertyNames.map { it.replace(" ", "%20") }
    return encodedProperties.joinToString(separator = ",")
}