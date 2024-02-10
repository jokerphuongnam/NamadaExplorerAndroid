package com.monsjoker.namadaexplorer.utils

import java.text.DecimalFormat

private val numberFormat = DecimalFormat("#,###")

fun Int.formattedWithCommas(): String {
    return numberFormat.format(this)
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)