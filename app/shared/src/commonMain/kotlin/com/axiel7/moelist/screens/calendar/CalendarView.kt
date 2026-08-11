package com.axiel7.moelist.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.data.model.media.WeekDay
import com.axiel7.moelist.data.utils.SeasonCalendar
import com.axiel7.moelist.ui.base.model.ListStatus.Companion.toBo
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.composables.TabRowWithPager
import com.axiel7.moelist.ui.composables.media.MEDIA_POSTER_SMALL_WIDTH
import com.axiel7.moelist.ui.composables.media.MediaItemVertical
import com.axiel7.moelist.ui.composables.media.MediaItemVerticalPlaceholder
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.calendar
import com.axiel7.moelist.ui.generated.resources.more_vert_24
import com.axiel7.moelist.ui.generated.resources.on_my_list
import com.axiel7.moelist.ui.generated.resources.round_check_24
import com.axiel7.moelist.ui.generated.resources.round_close_24
import com.axiel7.moelist.ui.generated.resources.show_more
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CalendarView(
    navActionManager: NavActionManager
) {
    val viewModel: CalendarViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarContent(
        uiState = uiState,
        event = viewModel,
        navActionManager = navActionManager,
    )
}

@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    event: CalendarEvent?,
    navActionManager: NavActionManager,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.message != null) {
        LaunchedEffect(uiState.message) {
            snackbarHostState.showSnackbar(uiState.message)
            event?.onMessageDisplayed()
        }
    }

    DefaultScaffoldWithTopAppBar(
        title = stringResource(UiRes.string.calendar),
        navigateBack = dropUnlessResumed { navActionManager.goBack() },
        actions = {
            AppBarActions(
                onMyList = uiState.onMyList,
                onMyListChanged = { event?.onMyListChanged(it) },
            )
        },
        snackbarHostState = snackbarHostState,
        contentWindowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal)
    ) { padding ->
        TabRowWithPager(
            tabs = WeekDay.tabRowItems,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            initialPage = SeasonCalendar.currentWeekday.ordinal,
            isTabScrollable = true
        ) { page ->
            val weekday = page + 1
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = MEDIA_POSTER_SMALL_WIDTH.dp),
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                items(
                    items = uiState.weekAnime(weekday),
                    contentType = { it }
                ) { item ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        MediaItemVertical(
                            imageUrl = item.node.mainPicture?.large,
                            title = item.node.title(uiState.preferredTitle),
                            badgeContent = item.node.myListStatus?.status?.toBo()?.let { status ->
                                {
                                    Icon(
                                        painter = painterResource(status.icon),
                                        contentDescription = status.localized(),
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            },
                            subtitle = {
                                Text(
                                    text = item.node.broadcast?.localStartTime() ?: "??",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            },
                            minLines = 2,
                            onClick = dropUnlessResumed {
                                navActionManager.toMediaDetails(MediaType.ANIME, item.node.id)
                            }
                        )
                    }
                }
                if (uiState.isLoading) {
                    items(10) {
                        MediaItemVerticalPlaceholder(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AppBarActions(
    onMyList: Boolean?,
    onMyListChanged: (Boolean?) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuOpened by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(
            onClick = { menuOpened = !menuOpened },
            shapes = IconButtonDefaults.shapes(),
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.more_vert_24),
                contentDescription = stringResource(UiRes.string.show_more),
            )
        }
        DropdownMenu(
            expanded = menuOpened,
            onDismissRequest = { menuOpened = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(UiRes.string.on_my_list)) },
                onClick = {
                    onMyListChanged(
                        when (onMyList) {
                            null -> true
                            true -> false
                            false -> null
                        }
                    )
                    menuOpened = false
                },
                leadingIcon = {
                    if (onMyList != null) {
                        Icon(
                            painter = painterResource(
                                if (onMyList) UiRes.drawable.round_check_24 else UiRes.drawable.round_close_24
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun CalendarPreview() {
    MoeListTheme {
        Surface {
            CalendarContent(
                uiState = CalendarUiState(),
                event = null,
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}