package com.axiel7.moelist.main

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.ui.base.navigation.Navigator
import com.axiel7.moelist.main.composables.MainBottomNavBar
import com.axiel7.moelist.main.composables.MainNavigationRail
import com.axiel7.moelist.main.composables.MainTopAppBar
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import kotlinx.coroutines.launch

@Composable
fun MainView(
    isCompactScreen: Boolean,
    isLoggedIn: Boolean,
    useListTabs: Boolean,
    isBottomDestination: Boolean,
    navigator: Navigator,
    navActionManager: NavActionManager,
    saveLastTab: (Int) -> Unit,
    pinnedNavBar: Boolean,
    profilePicture: String?,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    var topBarHeightPx by remember { mutableFloatStateOf(0f) }
    val topBarOffsetY = remember { Animatable(0f) }

    Scaffold(
        topBar = {
            if (isCompactScreen) {
                MainTopAppBar(
                    profilePicture = profilePicture,
                    isVisible = isBottomDestination,
                    navActionManager = navActionManager,
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = topBarOffsetY.value
                        }
                )
            }
        },
        bottomBar = {
            if (isCompactScreen) {
                MainBottomNavBar(
                    navigator = navigator,
                    navActionManager = navActionManager,
                    isVisible = isBottomDestination || pinnedNavBar,
                    onItemSelected = saveLastTab,
                    showTopBar = {
                        scope.launch {
                            topBarOffsetY.animateTo(0f)
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal)
    ) { padding ->
        if (!isCompactScreen) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(padding)
            ) {
                MainNavigationRail(
                    navigator = navigator,
                    onItemSelected = saveLastTab,
                    modifier = Modifier.safeDrawingPadding(),
                )
                MainNavigation(
                    navigator = navigator,
                    navActionManager = navActionManager,
                    isLoggedIn = isLoggedIn,
                    isCompactScreen = false,
                    useListTabs = useListTabs,
                    modifier = Modifier,
                    padding = PaddingValues(),
                    topBarHeightPx = topBarHeightPx,
                    topBarOffsetY = topBarOffsetY.value,
                    topBarAnimateTo = topBarOffsetY::animateTo,
                    topBarSnapTo = topBarOffsetY::snapTo,
                )
            }
        } else {
            LaunchedEffect(padding) {
                topBarHeightPx = density.run { padding.calculateTopPadding().toPx() }
            }
            MainNavigation(
                navigator = navigator,
                navActionManager = navActionManager,
                isLoggedIn = isLoggedIn,
                isCompactScreen = true,
                useListTabs = useListTabs,
                modifier = Modifier.padding(
                    start = padding.calculateStartPadding(LocalLayoutDirection.current),
                    end = padding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = if (pinnedNavBar) padding.calculateBottomPadding() else 0.dp,
                ),
                padding = PaddingValues(
                    start = padding.calculateStartPadding(LocalLayoutDirection.current),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = if (pinnedNavBar) 0.dp else padding.calculateBottomPadding(),
                ),
                topBarHeightPx = topBarHeightPx,
                topBarOffsetY = topBarOffsetY.value,
                topBarAnimateTo = topBarOffsetY::animateTo,
                topBarSnapTo = topBarOffsetY::snapTo,
            )
        }
    }
}