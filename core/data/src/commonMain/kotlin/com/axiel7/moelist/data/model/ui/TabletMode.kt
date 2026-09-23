package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.mode_always
import com.axiel7.moelist.ui.generated.resources.mode_auto
import com.axiel7.moelist.ui.generated.resources.mode_landscape
import com.axiel7.moelist.ui.generated.resources.mode_never
import kotlinx.collections.immutable.toImmutableMap
import org.jetbrains.compose.resources.stringResource

@Stable
enum class TabletMode : Localizable {
    AUTO,
    ALWAYS,
    LANDSCAPE,
    NEVER;
    
    val stringRes
        get() = when (this) {
            AUTO -> UiRes.string.mode_auto
            ALWAYS -> UiRes.string.mode_always
            LANDSCAPE -> UiRes.string.mode_landscape
            NEVER -> UiRes.string.mode_never
        }

    @Composable
    override fun localized() = stringResource(stringRes)

    companion object {
        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}