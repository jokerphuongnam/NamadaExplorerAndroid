package com.monsjoker.namadaexplorer.utils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

private val dateFormatter = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())

fun Date.timeAgoString(now: Date = Date()): String {
    val timeDifference = TimeUnit.MILLISECONDS.toSeconds(now.time - this.time)

    return when {
        timeDifference < 60 -> "Just now"
        timeDifference < 120 -> "1 minute ago"
        timeDifference < 3600 -> "${timeDifference / 60} minutes ago"
        timeDifference < 7200 -> "1 hour ago"
        timeDifference < 86400 -> "${timeDifference / 3600} hours ago"
        timeDifference < 172800 -> "1 day ago"
        timeDifference < 2592000 -> "${timeDifference / 86400} days ago" // 30 days
        else -> stringDate
    }
}

val Date.stringDate: String
    get() = dateFormatter.format(this)

operator fun Date.minus(other: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MILLISECOND, (-1 * other.time.toInt()))
    return calendar.time
}

operator fun Date.plus(other: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MILLISECOND, other.time.toInt())
    return calendar.time
}