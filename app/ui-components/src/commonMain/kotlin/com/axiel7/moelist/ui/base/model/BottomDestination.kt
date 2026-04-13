package com.axiel7.moelist.ui.base.model

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.ui.base.navigation.Route
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_more_horizontal
import com.axiel7.moelist.ui.generated.resources.ic_outline_book_24
import com.axiel7.moelist.ui.generated.resources.ic_outline_home_24
import com.axiel7.moelist.ui.generated.resources.ic_outline_local_movies_24
import com.axiel7.moelist.ui.generated.resources.ic_outline_person_24
import com.axiel7.moelist.ui.generated.resources.ic_round_book_24
import com.axiel7.moelist.ui.generated.resources.ic_round_home_24
import com.axiel7.moelist.ui.generated.resources.ic_round_local_movies_24
import com.axiel7.moelist.ui.generated.resources.ic_round_person_24
import com.axiel7.moelist.ui.generated.resources.more
import com.axiel7.moelist.ui.generated.resources.title_anime_list
import com.axiel7.moelist.ui.generated.resources.title_home
import com.axiel7.moelist.ui.generated.resources.title_manga_list
import com.axiel7.moelist.ui.generated.resources.title_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

sealed class BottomDestination(
    val value: String,
    val index: Int,
    val route: Route,
    val title: StringResource,
    val icon: DrawableResource,
    val iconSelected: DrawableResource,
) {
    data object Home : BottomDestination(
        value = "home",
        index = 0,
        route = Route.Tab.Home,
        title = UiRes.string.title_home,
        icon = UiRes.drawable.ic_outline_home_24,
        iconSelected = UiRes.drawable.ic_round_home_24
    )

    data object AnimeList : BottomDestination(
        value = "anime",
        index = 1,
        route = Route.Tab.Anime(mediaType = MediaType.ANIME),
        title = UiRes.string.title_anime_list,
        icon = UiRes.drawable.ic_outline_local_movies_24,
        iconSelected = UiRes.drawable.ic_round_local_movies_24
    )

    data object MangaList : BottomDestination(
        value = "manga",
        index = 2,
        route = Route.Tab.Manga(MediaType.MANGA),
        title = UiRes.string.title_manga_list,
        icon = UiRes.drawable.ic_outline_book_24,
        iconSelected = UiRes.drawable.ic_round_book_24
    )

    data object Profile : BottomDestination(
        value = "profile",
        index = 4,
        route = Route.Profile,
        title = UiRes.string.title_profile,
        icon = UiRes.drawable.ic_outline_person_24,
        iconSelected = UiRes.drawable.ic_round_person_24
    )

    data object More : BottomDestination(
        value = "more",
        index = 3,
        route = Route.Tab.More,
        title = UiRes.string.more,
        icon = UiRes.drawable.ic_more_horizontal,
        iconSelected = UiRes.drawable.ic_more_horizontal
    )

    companion object {
        val routes = setOf(Home.route, AnimeList.route, MangaList.route, Profile.route, More.route)

        val values = listOf(Home, AnimeList, MangaList, More)

        val railValues = listOf(Home, AnimeList, MangaList, Profile, More)

        fun String.toBottomDestinationIndex() = values.find { it.value == this }?.index

        fun Int.toBottomDestinationRoute() = values.find { it.index == this }?.route

        fun NavKey.isBottomDestination() = values.any { it.route == this }

        @Composable
        fun BottomDestination.Icon(selected: Boolean) {
            androidx.compose.material3.Icon(
                painter = painterResource(if (selected) iconSelected else icon),
                contentDescription = stringResource(title)
            )
        }
    }
}