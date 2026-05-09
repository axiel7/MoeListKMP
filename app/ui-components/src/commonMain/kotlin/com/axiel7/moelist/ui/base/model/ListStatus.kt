package com.axiel7.moelist.ui.base.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.axiel7.moelist.data.model.base.LocalizableAndColorable
import com.axiel7.moelist.data.model.media.ListStatusDto
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.check_circle_outline_24
import com.axiel7.moelist.ui.generated.resources.completed
import com.axiel7.moelist.ui.generated.resources.delete_outline_24
import com.axiel7.moelist.ui.generated.resources.dropped
import com.axiel7.moelist.ui.generated.resources.ic_round_access_time_24
import com.axiel7.moelist.ui.generated.resources.on_hold
import com.axiel7.moelist.ui.generated.resources.pause_circle_outline_24
import com.axiel7.moelist.ui.generated.resources.play_circle_outline_24
import com.axiel7.moelist.ui.generated.resources.ptr
import com.axiel7.moelist.ui.generated.resources.ptw
import com.axiel7.moelist.ui.generated.resources.reading
import com.axiel7.moelist.ui.generated.resources.watching
import com.axiel7.moelist.ui.theme.StatColors.stat_completed_content_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_completed_content_light
import com.axiel7.moelist.ui.theme.StatColors.stat_completed_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_completed_light
import com.axiel7.moelist.ui.theme.StatColors.stat_current_content_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_current_content_light
import com.axiel7.moelist.ui.theme.StatColors.stat_current_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_current_light
import com.axiel7.moelist.ui.theme.StatColors.stat_dropped_content_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_dropped_content_light
import com.axiel7.moelist.ui.theme.StatColors.stat_dropped_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_dropped_light
import com.axiel7.moelist.ui.theme.StatColors.stat_on_hold_content_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_on_hold_content_light
import com.axiel7.moelist.ui.theme.StatColors.stat_on_hold_dark
import com.axiel7.moelist.ui.theme.StatColors.stat_on_hold_light
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Serializable
enum class ListStatus(
    val icon: DrawableResource
) : LocalizableAndColorable {
    @SerialName("watching")
    WATCHING(
        icon = UiRes.drawable.play_circle_outline_24
    ),

    @SerialName("reading")
    READING(
        icon = UiRes.drawable.play_circle_outline_24
    ),

    @SerialName("plan_to_watch")
    PLAN_TO_WATCH(
        icon = UiRes.drawable.ic_round_access_time_24
    ),

    @SerialName("plan_to_read")
    PLAN_TO_READ(
        icon = UiRes.drawable.ic_round_access_time_24
    ),

    @SerialName("completed")
    COMPLETED(
        icon = UiRes.drawable.check_circle_outline_24
    ),

    @SerialName("on_hold")
    ON_HOLD(
        icon = UiRes.drawable.pause_circle_outline_24
    ),

    @SerialName("dropped")
    DROPPED(
        icon = UiRes.drawable.delete_outline_24
    );

    val value get() = name.lowercase()

    fun isCurrent() = this == WATCHING || this == READING

    fun isPlanning() = this == PLAN_TO_WATCH || this == PLAN_TO_READ

    @Composable
    override fun localized() = stringResource(stringRes)

    val stringRes
        get() = when (this) {
            WATCHING -> UiRes.string.watching
            READING -> UiRes.string.reading
            COMPLETED -> UiRes.string.completed
            ON_HOLD -> UiRes.string.on_hold
            DROPPED -> UiRes.string.dropped
            PLAN_TO_WATCH -> UiRes.string.ptw
            PLAN_TO_READ -> UiRes.string.ptr
        }

    @Composable
    override fun primaryColor() = when (this) {
        WATCHING, READING -> if (isSystemInDarkTheme()) stat_current_dark else stat_current_light
        PLAN_TO_WATCH, PLAN_TO_READ -> MaterialTheme.colorScheme.surfaceVariant
        COMPLETED -> if (isSystemInDarkTheme()) stat_completed_dark else stat_completed_light
        ON_HOLD -> if (isSystemInDarkTheme()) stat_on_hold_dark else stat_on_hold_light
        DROPPED -> if (isSystemInDarkTheme()) stat_dropped_dark else stat_dropped_light
    }

    @Composable
    override fun onPrimaryColor() = when (this) {
        WATCHING, READING -> if (isSystemInDarkTheme()) stat_current_content_dark else stat_current_content_light
        PLAN_TO_WATCH, PLAN_TO_READ -> MaterialTheme.colorScheme.onSurfaceVariant
        COMPLETED -> if (isSystemInDarkTheme()) stat_completed_content_dark else stat_completed_content_light
        ON_HOLD -> if (isSystemInDarkTheme()) stat_on_hold_content_dark else stat_on_hold_content_light
        DROPPED -> if (isSystemInDarkTheme()) stat_dropped_content_dark else stat_dropped_content_light
    }

    companion object {

        fun ListStatusDto.toBo() = when (this) {
            ListStatusDto.WATCHING -> WATCHING
            ListStatusDto.READING -> READING
            ListStatusDto.PLAN_TO_WATCH -> PLAN_TO_WATCH
            ListStatusDto.PLAN_TO_READ -> PLAN_TO_READ
            ListStatusDto.COMPLETED -> COMPLETED
            ListStatusDto.ON_HOLD -> ON_HOLD
            ListStatusDto.DROPPED -> DROPPED
        }

        fun ListStatus.toDto() = when (this) {
            WATCHING -> ListStatusDto.WATCHING
            READING -> ListStatusDto.READING
            PLAN_TO_WATCH -> ListStatusDto.PLAN_TO_WATCH
            PLAN_TO_READ -> ListStatusDto.PLAN_TO_READ
            COMPLETED -> ListStatusDto.COMPLETED
            ON_HOLD -> ListStatusDto.ON_HOLD
            DROPPED -> ListStatusDto.DROPPED
        }

        val listStatusAnimeValues = arrayOf(WATCHING, PLAN_TO_WATCH, COMPLETED, ON_HOLD, DROPPED)

        val listStatusMangaValues = arrayOf(READING, PLAN_TO_READ, COMPLETED, ON_HOLD, DROPPED)

        fun listStatusValues(mediaType: MediaType) =
            if (mediaType == MediaType.ANIME) listStatusAnimeValues else listStatusMangaValues
    }
}
