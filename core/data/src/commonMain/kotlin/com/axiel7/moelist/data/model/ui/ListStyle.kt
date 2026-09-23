package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.list_mode_compact
import com.axiel7.moelist.ui.generated.resources.list_mode_grid
import com.axiel7.moelist.ui.generated.resources.list_mode_minimal
import com.axiel7.moelist.ui.generated.resources.list_mode_standard
import kotlinx.collections.immutable.toImmutableMap
import org.jetbrains.compose.resources.stringResource

@Stable
enum class ListStyle : Localizable {
    STANDARD,
    COMPACT,
    MINIMAL,
    GRID;

    val stringRes
        get() = when (this) {
            STANDARD -> UiRes.string.list_mode_standard
            COMPACT -> UiRes.string.list_mode_compact
            MINIMAL -> UiRes.string.list_mode_minimal
            GRID -> UiRes.string.list_mode_grid
        }

    @Composable
    override fun localized() = stringResource(stringRes)

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (e: IllegalArgumentException) {
            null
        }

        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}