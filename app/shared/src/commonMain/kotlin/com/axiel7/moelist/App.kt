package com.axiel7.moelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation3.runtime.rememberNavBackStack
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.data.model.ui.TabletMode
import com.axiel7.moelist.main.MainEvent
import com.axiel7.moelist.main.MainUiState
import com.axiel7.moelist.main.MainView
import com.axiel7.moelist.ui.base.model.BottomDestination
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.isBottomDestination
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.toBottomDestinationRoute
import com.axiel7.moelist.ui.base.navigation.DeepLink
import com.axiel7.moelist.ui.base.navigation.NavActionManager.Companion.rememberNavActionManager
import com.axiel7.moelist.ui.base.navigation.NavActionManager.Companion.savedStateConfiguration
import com.axiel7.moelist.ui.base.navigation.TopLevelBackStack
import com.axiel7.moelist.ui.theme.MoeListTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App(
    uiState: MainUiState,
    event: MainEvent,
    windowWidthSizeClass: WindowWidthSizeClass,
    lastTabOpened: Int,
    dynamicColorSeed: Color? = null,
    onThemeChange: ((isDarkTheme: Boolean) -> Unit)? = null
) {
    val uriHandler = LocalUriHandler.current
    val startKey = remember(lastTabOpened) {
        lastTabOpened.toBottomDestinationRoute() ?: BottomDestination.Home.route
    }
    val backStack = rememberNavBackStack(configuration = savedStateConfiguration, startKey)
    val topLevelBackStack = remember { TopLevelBackStack(startKey, backStack) }
    val navActionManager = rememberNavActionManager(topLevelBackStack)
    val isBottomDestination by remember {
        derivedStateOf {
            topLevelBackStack.backStack.lastOrNull()?.isBottomDestination() == true
        }
    }
    val isCompactScreen = when (uiState.tabletMode) {
        TabletMode.AUTO -> windowWidthSizeClass == WindowWidthSizeClass.Compact
        TabletMode.ALWAYS -> false
        TabletMode.LANDSCAPE -> windowWidthSizeClass == WindowWidthSizeClass.Compact
        TabletMode.NEVER -> true
    }

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, percent = 0.25)
                    .build()
            }
            .crossfade(true)
            .build()
    }

    LaunchedEffect(uiState.deepLink) {
        uiState.deepLink?.let { deepLink ->
            when (deepLink.type) {
                DeepLink.Type.ANIME -> {
                    navActionManager.toMediaDetails(MediaType.ANIME, deepLink.data as Int)
                }
                DeepLink.Type.MANGA -> {
                    navActionManager.toMediaDetails(MediaType.MANGA, deepLink.data as Int)
                }
                DeepLink.Type.LOGIN -> { event.getAccessToken(deepLink.data as String) }

                DeepLink.Type.EXTERNAL -> { uriHandler.openUri(deepLink.data as String) }
            }
        }
    }

    MoeListTheme(
        dynamicColorSeed = dynamicColorSeed
    ) {
        val backgroundColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            MainView(
                isCompactScreen = isCompactScreen,
                isLoggedIn = !uiState.accessToken.isNullOrEmpty(),
                useListTabs = uiState.useListTabs,
                isBottomDestination = isBottomDestination,
                topLevelBackStack = topLevelBackStack,
                navActionManager = navActionManager,
                saveLastTab = event::saveLastTab,
                pinnedNavBar = uiState.pinnedNavBar,
                profilePicture = uiState.profilePicture,
            )
        }
    }
}