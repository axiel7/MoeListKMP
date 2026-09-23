package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.last_used
import com.axiel7.moelist.ui.generated.resources.more
import com.axiel7.moelist.ui.generated.resources.title_anime_list
import com.axiel7.moelist.ui.generated.resources.title_home
import com.axiel7.moelist.ui.generated.resources.title_manga_list
import kotlinx.collections.immutable.toImmutableMap
import org.jetbrains.compose.resources.stringResource

@Stable
enum class StartTab(
    val value: String
) : Localizable {
    LAST_USED("last_used"),
    HOME("home"),
    ANIME("anime"),
    MANGA("manga"),
    MORE("more");

    val stringRes
        get() = when (this) {
            LAST_USED -> UiRes.string.last_used
            HOME -> UiRes.string.title_home
            ANIME -> UiRes.string.title_anime_list
            MANGA -> UiRes.string.title_manga_list
            MORE -> UiRes.string.more
        }

    @Composable
    override fun localized() = stringResource(stringRes)

    companion object {
        fun valueOf(tabName: String) = entries.find { it.value == tabName }

        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}