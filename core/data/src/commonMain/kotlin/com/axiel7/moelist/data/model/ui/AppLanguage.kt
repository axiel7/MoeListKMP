package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.arabic_native
import com.axiel7.moelist.ui.generated.resources.brazilian_native
import com.axiel7.moelist.ui.generated.resources.bulgarian_native
import com.axiel7.moelist.ui.generated.resources.chinese_simplified_native
import com.axiel7.moelist.ui.generated.resources.chinese_traditional_native
import com.axiel7.moelist.ui.generated.resources.czech_native
import com.axiel7.moelist.ui.generated.resources.english_native
import com.axiel7.moelist.ui.generated.resources.french_native
import com.axiel7.moelist.ui.generated.resources.german_native
import com.axiel7.moelist.ui.generated.resources.indonesian_native
import com.axiel7.moelist.ui.generated.resources.italian_native
import com.axiel7.moelist.ui.generated.resources.japanese_native
import com.axiel7.moelist.ui.generated.resources.polish_native
import com.axiel7.moelist.ui.generated.resources.portuguese_native
import com.axiel7.moelist.ui.generated.resources.russian_native
import com.axiel7.moelist.ui.generated.resources.slovak_native
import com.axiel7.moelist.ui.generated.resources.spanish_native
import com.axiel7.moelist.ui.generated.resources.theme_system
import com.axiel7.moelist.ui.generated.resources.turkish_native
import com.axiel7.moelist.ui.generated.resources.ukrainian_native
import kotlinx.collections.immutable.toImmutableMap

@Stable
enum class AppLanguage(val value: String) {
    FOLLOW_SYSTEM("follow_system"),
    ENGLISH("en"),
    ARABIC("ar-SA"),
    BULGARIAN("bg-BG"),
    CHINESE_SIMPLIFIED("zh-Hans"),
    CHINESE_TRADITIONAL("zh-Hant"),
    CZECH("cs"),
    FRENCH("fr"),
    GERMAN("de"),
    INDONESIAN("in-ID"),
    ITALIAN("it-IT"),
    JAPANESE("ja"),
    POLISH("pl-PL"),
    PORTUGUESE("pt-PT"),
    PORTUGUESE_BRAZILIAN("pt-BR"),
    RUSSIAN("ru-RU"),
    SLOVAK("sk"),
    SPANISH("es"),
    TURKISH("tr"),
    UKRAINIAN("uk-UA");

    val stringResNative
        get() = when (this) {
            FOLLOW_SYSTEM -> UiRes.string.theme_system
            ENGLISH -> UiRes.string.english_native
            ARABIC -> UiRes.string.arabic_native
            BULGARIAN -> UiRes.string.bulgarian_native
            CHINESE_SIMPLIFIED -> UiRes.string.chinese_simplified_native
            CHINESE_TRADITIONAL -> UiRes.string.chinese_traditional_native
            CZECH -> UiRes.string.czech_native
            FRENCH -> UiRes.string.french_native
            GERMAN -> UiRes.string.german_native
            INDONESIAN -> UiRes.string.indonesian_native
            ITALIAN -> UiRes.string.italian_native
            JAPANESE -> UiRes.string.japanese_native
            POLISH -> UiRes.string.polish_native
            PORTUGUESE -> UiRes.string.portuguese_native
            PORTUGUESE_BRAZILIAN -> UiRes.string.brazilian_native
            RUSSIAN -> UiRes.string.russian_native
            SLOVAK -> UiRes.string.slovak_native
            SPANISH -> UiRes.string.spanish_native
            TURKISH -> UiRes.string.turkish_native
            UKRAINIAN -> UiRes.string.ukrainian_native
        }

    companion object {
        fun valueOf(isoTag: String) = entries.find { it.value == isoTag }

        val entriesLocalized = entries.associateWith { it.stringResNative }.toImmutableMap()
    }
}