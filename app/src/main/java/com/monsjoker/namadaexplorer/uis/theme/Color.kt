package com.monsjoker.namadaexplorer.uis.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Yellow = Color(0xFFFFFF00)
val Black = Color(0xFF000000)
val White = Color(0XFFFFFFFF)
val Cyan = Color(0XFF00FFFF)

@Stable
val Color.Companion.LightYellow: Color
    get() {
        return Color(0xfffffec8)
    }

@Stable
val Color.Companion.DarkYellow: Color
    get() {
        return Color(0xff938200)
    }

@Stable
val Color.Companion.LightGreen: Color
    get() {
        return Color(0xff77ff77)
    }

@Stable
val Color.Companion.DarkGreen: Color
    get() {
        return Color(0xff0a5d00)
    }


@Stable
val Color.Companion.LightRed: Color
    get() {
        return Color(0xfff9aeae)
    }

@Stable
val Color.Companion.DarkRed: Color
    get() {
        return Color(0xff800000)
    }