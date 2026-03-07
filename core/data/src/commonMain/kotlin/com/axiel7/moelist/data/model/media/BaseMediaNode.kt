package com.axiel7.moelist.data.model.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.anime.AnimeNode
import com.axiel7.moelist.data.model.anime.NodeSeasonal
import com.axiel7.moelist.data.model.manga.MangaNode
import com.axiel7.moelist.data.utils.UNKNOWN_CHAR
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.num_chapters
import com.axiel7.moelist.ui.generated.resources.num_episodes
import org.jetbrains.compose.resources.pluralStringResource

@Stable
abstract class BaseMediaNode {
    abstract val id: Int
    abstract val title: String
    abstract val alternativeTitles: AlternativeTitles?
    abstract val mainPicture: MainPicture?
    open val numListUsers: Int? = null
    abstract val mediaFormat: MediaFormat?
    abstract val status: MediaStatus?
    abstract val mean: Float?
    open val myListStatus: BasicMyListStatus? = null

    val mediaType
        get() = if (this is MangaNode) MediaType.MANGA else MediaType.ANIME

    fun title(language: TitleLanguage) = when (language) {
        TitleLanguage.ROMAJI -> title
        TitleLanguage.ENGLISH ->
            if (alternativeTitles?.en.isNullOrBlank()) title
            else alternativeTitles?.en ?: title

        TitleLanguage.JAPANESE ->
            if (alternativeTitles?.ja.isNullOrBlank()) title
            else alternativeTitles?.ja ?: title
    }

    fun totalDuration() = when (this) {
        is AnimeNode -> this.numEpisodes.takeIf { it != 0 }
        is MangaNode -> this.numChapters.takeIf { it != 0 }
        is NodeSeasonal -> this.numEpisodes.takeIf { it != 0 }
        else -> null
    }

    fun totalVolumes() = (this as? MangaNode)?.numVolumes?.takeIf { it != 0 }

    @Composable
    fun durationText() = when (this) {
        is AnimeNode, is NodeSeasonal -> {
            val totalDuration = this.totalDuration()
            if (totalDuration != null && totalDuration > 0) {
                pluralStringResource(UiRes.plurals.num_episodes, totalDuration, totalDuration)
            } else UNKNOWN_CHAR
        }

        is MangaNode -> {
            if (numChapters != null && numChapters > 0) {
                pluralStringResource(UiRes.plurals.num_chapters, numChapters, numChapters)
            } else UNKNOWN_CHAR
        }

        else -> UNKNOWN_CHAR
    }
}