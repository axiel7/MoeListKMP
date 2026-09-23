package com.axiel7.moelist.data.model.media

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.english
import com.axiel7.moelist.ui.generated.resources.japanese
import com.axiel7.moelist.ui.generated.resources.romaji
import kotlinx.collections.immutable.toImmutableMap

@Stable
enum class TitleLanguage {
    ROMAJI, ENGLISH, JAPANESE;

    val stringRes
        get() = when (this) {
            ROMAJI -> UiRes.string.romaji
            ENGLISH -> UiRes.string.english
            JAPANESE -> UiRes.string.japanese
        }

    companion object {
        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}