package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.theme_dark
import com.axiel7.moelist.ui.generated.resources.theme_light
import com.axiel7.moelist.ui.generated.resources.theme_system
import kotlinx.collections.immutable.toImmutableMap

@Stable
enum class ThemeStyle {
    FOLLOW_SYSTEM, LIGHT, DARK;

    val stringRes
        get() = when (this) {
            FOLLOW_SYSTEM -> UiRes.string.theme_system
            LIGHT -> UiRes.string.theme_light
            DARK -> UiRes.string.theme_dark
        }

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (_: IllegalArgumentException) {
            null
        }

        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}