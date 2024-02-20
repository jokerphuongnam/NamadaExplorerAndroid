package com.monsjoker.namadaexplorer.utils

fun List<Number>.calculateCumulativeRatios(): List<Float> {
    val sum = this.sumOf { it.toDouble() }
    var cumulativeRatio = 0.0
    return this.map { value ->
        val ratio = value.toDouble() / sum
        cumulativeRatio += ratio
        cumulativeRatio.toFloat()
    }
}