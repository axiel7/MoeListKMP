package com.axiel7.moelist.data.model.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.members
import com.axiel7.moelist.ui.generated.resources.sort_last_updated
import com.axiel7.moelist.ui.generated.resources.sort_score
import com.axiel7.moelist.ui.generated.resources.sort_title
import com.axiel7.moelist.ui.generated.resources.start_date
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Stable
enum class MediaSort(val value: String) : Localizable {
    ANIME_TITLE("anime_title"),
    ANIME_SCORE("anime_score"),
    ANIME_NUM_USERS("anime_num_list_users"),
    ANIME_START_DATE("anime_start_date"),
    SCORE("list_score"),
    UPDATED("list_updated_at"),
    MANGA_TITLE("manga_title"),
    MANGA_START_DATE("manga_start_date");

    override fun toString() = value

    @Composable
    override fun localized() = when (this) {
        ANIME_TITLE -> stringResource(UiRes.string.sort_title)
        ANIME_SCORE -> stringResource(UiRes.string.sort_score)
        ANIME_NUM_USERS -> stringResource(UiRes.string.members)
        ANIME_START_DATE -> stringResource(UiRes.string.start_date)
        SCORE -> stringResource(UiRes.string.sort_score)
        UPDATED -> stringResource(UiRes.string.sort_last_updated)
        MANGA_TITLE -> stringResource(UiRes.string.sort_title)
        MANGA_START_DATE -> stringResource(UiRes.string.start_date)
    }

    companion object {
        fun valueOf(malValue: String) = entries.firstOrNull { it.value == malValue }

        val animeListSortItems = persistentListOf(ANIME_TITLE, SCORE, UPDATED, ANIME_START_DATE)

        val mangaListSortItems = persistentListOf(MANGA_TITLE, SCORE, UPDATED, MANGA_START_DATE)
    }
}