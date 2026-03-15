package com.axiel7.moelist.screens.ranking

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.data.model.media.RankingType.Companion.rankingAnimeValues
import com.axiel7.moelist.data.model.media.RankingType.Companion.rankingMangaValues
import com.axiel7.moelist.data.model.ui.TabRowItem
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.base.navigation.Route
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.composables.TabRowWithPager
import com.axiel7.moelist.screens.ranking.list.MediaRankingListView
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.anime_ranking
import com.axiel7.moelist.ui.generated.resources.manga_ranking
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.get

@Composable
fun MediaRankingView(
    arguments: Route.MediaRanking,
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val tabRowItems = remember {
        (if (arguments.mediaType == MediaType.ANIME) rankingAnimeValues else rankingMangaValues)
            .map {
                TabRowItem(value = it, title = it.stringRes)
            }.toTypedArray()
    }

    DefaultScaffoldWithTopAppBar(
        title = stringResource(
            if (arguments.mediaType == MediaType.ANIME) UiRes.string.anime_ranking
            else UiRes.string.manga_ranking
        ),
        navigateBack = navActionManager::goBack,
        contentWindowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal)
    ) { padding ->
        TabRowWithPager(
            tabs = tabRowItems,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            isTabScrollable = true
        ) {
            MediaRankingListView(
                arguments = arguments,
                rankingType = tabRowItems[it].value,
                isCompactScreen = isCompactScreen,
                snackbarHostState = snackbarHostState,
                navActionManager = navActionManager,
            )
        }
    }
}