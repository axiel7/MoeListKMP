package com.axiel7.moelist.ui.utils

import androidx.compose.ui.graphics.Color

object ColorUtils {

    fun colorFromHex(color: String?): Color? {
        if (color.isNullOrEmpty()) return null
        return try {
            val hex = color.removePrefix("#")
            val fullHex = if (hex.length == 6) "FF$hex" else hex
            Color(fullHex.toLong(16).toInt())
        } catch (_: Exception) {
            null
        }
    }
}