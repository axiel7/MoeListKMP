package com.axiel7.moelist.screens.home

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.data.utils.SeasonCalendar
import com.axiel7.moelist.screens.home.composables.AiringAnimeHorizontalItem
import com.axiel7.moelist.screens.home.composables.HomeCard
import com.axiel7.moelist.ui.base.model.ListStatus.Companion.toBo
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.HeaderHorizontalList
import com.axiel7.moelist.ui.composables.collapsable
import com.axiel7.moelist.ui.composables.media.MEDIA_ITEM_VERTICAL_HEIGHT
import com.axiel7.moelist.ui.composables.media.MEDIA_POSTER_SMALL_HEIGHT
import com.axiel7.moelist.ui.composables.media.MediaItemDetailedPlaceholder
import com.axiel7.moelist.ui.composables.media.MediaItemVertical
import com.axiel7.moelist.ui.composables.media.MediaItemVerticalPlaceholder
import com.axiel7.moelist.ui.composables.score.SmallScoreIndicator
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.anime_ranking
import com.axiel7.moelist.ui.generated.resources.calendar
import com.axiel7.moelist.ui.generated.resources.error_server
import com.axiel7.moelist.ui.generated.resources.ic_round_casino_24
import com.axiel7.moelist.ui.generated.resources.ic_round_event_24
import com.axiel7.moelist.ui.generated.resources.ic_round_menu_book_24
import com.axiel7.moelist.ui.generated.resources.ic_round_movie_24
import com.axiel7.moelist.ui.generated.resources.manga_ranking
import com.axiel7.moelist.ui.generated.resources.no_recommendations
import com.axiel7.moelist.ui.generated.resources.nothing_today
import com.axiel7.moelist.ui.generated.resources.please_login_to_use_this_feature
import com.axiel7.moelist.ui.generated.resources.random
import com.axiel7.moelist.ui.generated.resources.recommendations
import com.axiel7.moelist.ui.generated.resources.seasonal_chart
import com.axiel7.moelist.ui.generated.resources.this_season
import com.axiel7.moelist.ui.generated.resources.today
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

@Composable
fun HomeView(
    isLoggedIn: Boolean,
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float,
    topBarOffsetY: Float,
    topBarAnimateTo: suspend (Float) -> Unit,
    topBarSnapTo: suspend (Float) -> Unit,
    padding: PaddingValues,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeViewContent(
        uiState = uiState,
        event = viewModel,
        isLoggedIn = isLoggedIn,
        isCompactScreen = isCompactScreen,
        navActionManager = navActionManager,
        topBarHeightPx = topBarHeightPx,
        topBarOffsetY = topBarOffsetY,
        topBarAnimateTo = topBarAnimateTo,
        topBarSnapTo = topBarSnapTo,
        padding = padding,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun HomeViewContent(
    uiState: HomeUiState,
    event: HomeEvent?,
    isLoggedIn: Boolean,
    isCompactScreen: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float = 0f,
    topBarOffsetY: Float = 0f,
    topBarAnimateTo: suspend (Float) -> Unit = {},
    topBarSnapTo: suspend (Float) -> Unit = {},
    padding: PaddingValues = PaddingValues(),
) {
    //val context = LocalContext.current
    val scrollState = rememberScrollState()
    val airingListState = rememberLazyListState()
    val seasonListState = rememberLazyListState()
    val recommendListState = rememberLazyListState()

    LaunchedEffect(uiState.message) {
        if (uiState.message != null) {
            //context.showToast(uiState.message)
            event?.onMessageDisplayed()
        }
    }

    LaunchedEffect(isLoggedIn) {
        event?.initRequestChain(isLoggedIn)
    }

    @Composable
    fun RowScope.ChipRow1() {
        HomeCard(
            text = stringResource(UiRes.string.anime_ranking),
            icon = UiRes.drawable.ic_round_movie_24,
            modifier = Modifier.weight(1f),
            onClick = dropUnlessResumed {
                navActionManager.toMediaRanking(MediaType.ANIME)
            },
        )

        HomeCard(
            text = stringResource(UiRes.string.manga_ranking),
            icon = UiRes.drawable.ic_round_menu_book_24,
            modifier = Modifier.weight(1f),
            onClick = dropUnlessResumed {
                navActionManager.toMediaRanking(MediaType.MANGA)
            },
        )
    }

    @Composable
    fun RowScope.ChipRow2() {
        HomeCard(
            text = stringResource(UiRes.string.seasonal_chart),
            icon = SeasonCalendar.currentSeason.icon,
            modifier = Modifier.weight(1f),
            onClick = dropUnlessResumed {
                navActionManager.toSeasonChart()
            },
        )

        HomeCard(
            text = stringResource(UiRes.string.calendar),
            icon = UiRes.drawable.ic_round_event_24,
            modifier = Modifier.weight(1f),
            onClick = dropUnlessResumed {
                navActionManager.toCalendar()
            },
        )
    }

    Column(
        modifier = Modifier
            .collapsable(
                state = scrollState,
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY,
                animateTo = topBarAnimateTo,
                snapTo = topBarSnapTo,
            )
            .verticalScroll(scrollState)
            .padding(padding)
    ) {
        // Chips
        if (isCompactScreen) {
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 16.dp)
            ) {
                ChipRow1()
            }

            Row(
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 16.dp)
            ) {
                ChipRow2()
            }
        } else {
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 16.dp)
            ) {
                ChipRow1()
                ChipRow2()
            }
        }

        // Airing
        HeaderHorizontalList(
            text = stringResource(UiRes.string.today),
            onClick = dropUnlessResumed { navActionManager.toCalendar() }
        )
        if (!uiState.isLoading && uiState.todayAnimes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MEDIA_POSTER_SMALL_HEIGHT.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(UiRes.string.nothing_today),
                    textAlign = TextAlign.Center
                )
            }
        } else LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .sizeIn(minHeight = MEDIA_POSTER_SMALL_HEIGHT.dp),
            state = airingListState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = airingListState)
        ) {
            items(
                items = uiState.todayAnimes,
                key = { it.node.id },
                contentType = { it.node }
            ) {
                AiringAnimeHorizontalItem(
                    item = it,
                    hideScore = uiState.hideScore,
                    onClick = dropUnlessResumed {
                        navActionManager.toMediaDetails(MediaType.ANIME, it.node.id)
                    }
                )
            }
            if (uiState.isLoading) {
                items(5) {
                    MediaItemDetailedPlaceholder()
                }
            }
        }

        // This Season
        HeaderHorizontalList(
            text = stringResource(UiRes.string.this_season),
            onClick = dropUnlessResumed { navActionManager.toSeasonChart() }
        )
        if (!uiState.isLoading && uiState.seasonAnimes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MEDIA_POSTER_SMALL_HEIGHT.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(UiRes.string.error_server),
                    textAlign = TextAlign.Center
                )
            }
        } else LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .sizeIn(minHeight = MEDIA_ITEM_VERTICAL_HEIGHT.dp),
            state = seasonListState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = seasonListState)
        ) {
            items(
                items = uiState.seasonAnimes,
                key = { it.node.id },
                contentType = { it.node }
            ) {
                MediaItemVertical(
                    imageUrl = it.node.mainPicture?.large,
                    title = it.node.userPreferredTitle(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    badgeContent = it.node.myListStatus?.status?.toBo()?.let { status ->
                        {
                            Icon(
                                painter = painterResource(status.icon),
                                contentDescription = status.localized(),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    subtitle = if (!uiState.hideScore) {
                        {
                            SmallScoreIndicator(
                                score = it.node.mean,
                                fontSize = 13.sp
                            )
                        }
                    } else null,
                    minLines = 2,
                    onClick = dropUnlessResumed {
                        navActionManager.toMediaDetails(MediaType.ANIME, it.node.id)
                    }
                )
            }
            if (uiState.isLoading) {
                items(10) {
                    MediaItemVerticalPlaceholder()
                }
            }
        }

        //Recommended
        HeaderHorizontalList(
            text = stringResource(UiRes.string.recommendations),
            onClick = dropUnlessResumed { navActionManager.toRecommendations() }
        )
        if (!isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MEDIA_POSTER_SMALL_HEIGHT.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(UiRes.string.please_login_to_use_this_feature),
                    textAlign = TextAlign.Center
                )
            }
        } else if (!uiState.isLoading && uiState.recommendedAnimes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MEDIA_POSTER_SMALL_HEIGHT.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(UiRes.string.no_recommendations),
                    textAlign = TextAlign.Center
                )
            }
        } else LazyRow(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .sizeIn(minHeight = MEDIA_ITEM_VERTICAL_HEIGHT.dp),
            state = recommendListState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = recommendListState)
        ) {
            items(
                items = uiState.recommendedAnimes,
                key = { it.node.id },
                contentType = { it.node }
            ) {
                MediaItemVertical(
                    imageUrl = it.node.mainPicture?.large,
                    title = it.node.userPreferredTitle(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    subtitle = if (!uiState.hideScore) {
                        {
                            SmallScoreIndicator(
                                score = it.node.mean,
                                fontSize = 13.sp
                            )
                        }
                    } else null,
                    minLines = 2,
                    onClick = dropUnlessResumed {
                        navActionManager.toMediaDetails(MediaType.ANIME, it.node.id)
                    }
                )
            }
            if (uiState.isLoading) {
                items(10) {
                    MediaItemVerticalPlaceholder()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            //Random
            OutlinedButton(
                onClick = dropUnlessResumed {
                    val type = if (Random.nextBoolean()) MediaType.ANIME else MediaType.MANGA
                    val id = Random.nextInt(from = 0, until = 6000)
                    navActionManager.toMediaDetails(type, id)
                },
                shapes = ButtonDefaults.shapes(),
            ) {
                Icon(
                    painter = painterResource(UiRes.drawable.ic_round_casino_24),
                    contentDescription = stringResource(UiRes.string.random),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp)
                )
                Text(
                    text = stringResource(UiRes.string.random),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    MoeListTheme {
        Surface {
            HomeViewContent(
                uiState = HomeUiState(),
                event = null,
                isLoggedIn = false,
                isCompactScreen = false,
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}
