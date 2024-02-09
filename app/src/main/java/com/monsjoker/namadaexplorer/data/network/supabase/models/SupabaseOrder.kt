package com.monsjoker.namadaexplorer.data.network.supabase.models

class SupabaseOrder(field: SortField, order: SortOrder) {
    val text: String = "${field.rawValue}.${order.rawValue}"

    enum class SortField(val rawValue: String) {
        VOTING_POWER("voting_power"),
        HEIGHT("height")
    }

    enum class SortOrder(val rawValue: String) {
        DESC("desc"),
        ASC("asc")
    }
}

fun List<SupabaseOrder>.createQueryString(): String {
    if (isEmpty()) return "*"
    val propertyNames = map { it.text }
    val encodedProperties = propertyNames.map { it.replace(" ", "%20") }
    return encodedProperties.joinToString(separator = ",")
}