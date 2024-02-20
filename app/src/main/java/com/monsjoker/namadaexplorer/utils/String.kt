package com.monsjoker.namadaexplorer.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale


private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
private val dateFormatterWithoutMillis =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

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

val String.base64Number: Long
    get() {
        return try {
            val bytes = Base64.getDecoder().decode(this)
            if (bytes.size >= 8) {
                    var number: Long = 0
                    for (i in 0..7) {
                        number = number or ((bytes[i].toLong() and 0xFFL) shl (8 * i))
                    }
                    number
                } else {
                0
            }
        } catch (e: IllegalArgumentException) {
            0
        }
    }

fun String.copyToClipboard(context: Context) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("password", this)
    clipboardManager.setPrimaryClip(clip)
}

fun String.insertSpacesBeforeUppercase(): String {
    val builder = StringBuilder()
    for (char in this) {
        if (char.isUpperCase()) {
            builder.append(' ')
        }
        builder.append(char)
    }
    return builder.toString().trim()
}

val String.colorByHashCode: Color
get() {
    val hash = hashCode()
    val red = (hash and 0xFF0000 shr 16) / 255.0f
    val green = (hash and 0x00FF00 shr 8) / 255.0f
    val blue = (hash and 0x0000FF) / 255.0f
    return Color(red, green, blue, 1.0f)
}