package com.monsjoker.namadaexplorer.utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
private val dateFormatterWithoutMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

val String.date: Date?
    get() {
        val timeFormats = listOf(dateFormatter, dateFormatterWithoutMillis)
        for (format in timeFormats) {
            try {
                return format.parse(this)
            } catch (e: Exception) {
                // If parsing fails, try the next format
            }
        }
        return null
    }
