package com.monsjoker.namadaexplorer.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.visibility(visible: Boolean): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            if (visible) {
                placeable.placeRelative(0, 0)
            }
        }
    }
}