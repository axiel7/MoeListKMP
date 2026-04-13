package com.axiel7.moelist.ui.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import com.axiel7.moelist.data.model.media.MediaType

@Immutable
class NavActionManager(
    private val navigator: INavigator,
) {
    fun goBack() {
        navigator.goBack()
    }

    fun toMediaRanking(mediaType: MediaType) {
        navigator.navigate(Route.MediaRanking(mediaType))
    }

    fun toMediaDetails(mediaType: MediaType, id: Int) {
        navigator.navigate(
            Route.MediaDetails(
                mediaType = mediaType,
                mediaId = id,
            )
        )
    }

    fun toCalendar() {
        navigator.navigate(Route.Calendar)
    }

    fun toSeasonChart() {
        navigator.navigate(Route.SeasonChart)
    }

    fun toRecommendations() {
        navigator.navigate(Route.Recommendations)
    }

    fun toFullPoster(pictures: List<String>) {
        navigator.navigate(Route.FullPoster(pictures))
    }

    fun toSettings() {
        navigator.navigate(Route.Settings)
    }

    fun toListStyleSettings() {
        navigator.navigate(Route.ListStyleSettings)
    }

    fun toNotifications() {
        navigator.navigate(Route.Notifications)
    }

    fun toAbout() {
        navigator.navigate(Route.About)
    }

    fun toCredits() {
        navigator.navigate(Route.Credits)
    }

    fun toSearch(mediaType: MediaType) {
        navigator.navigate(Route.Search(mediaType))
    }

    fun toProfile() {
        navigator.navigate(Route.Profile)
    }

    companion object {
        @Composable
        fun rememberNavActionManager(
            navigator: INavigator = PreviewNavigator()
        ) = remember {
            NavActionManager(navigator)
        }
    }
}
