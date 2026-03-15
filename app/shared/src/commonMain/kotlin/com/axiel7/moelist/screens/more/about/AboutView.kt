package com.axiel7.moelist.screens.more.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.utils.DISCORD_SERVER_URL
import com.axiel7.moelist.data.utils.GITHUB_REPO_URL
import com.axiel7.moelist.screens.more.composables.MoreItem
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.about
import com.axiel7.moelist.ui.generated.resources.app_version
import com.axiel7.moelist.ui.generated.resources.credits
import com.axiel7.moelist.ui.generated.resources.credits_summary
import com.axiel7.moelist.ui.generated.resources.discord
import com.axiel7.moelist.ui.generated.resources.discord_summary
import com.axiel7.moelist.ui.generated.resources.github
import com.axiel7.moelist.ui.generated.resources.github_summary
import com.axiel7.moelist.ui.generated.resources.ic_discord
import com.axiel7.moelist.ui.generated.resources.ic_github
import com.axiel7.moelist.ui.generated.resources.ic_moelist_logo
import com.axiel7.moelist.ui.generated.resources.ic_round_group_24
import com.axiel7.moelist.ui.generated.resources.version
import com.axiel7.moelist.ui.theme.MoeListTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutView(
    navActionManager: NavActionManager
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current
    var versionClicks by remember { mutableIntStateOf(0) }

    DefaultScaffoldWithTopAppBar(
        title = stringResource(UiRes.string.about),
        navigateBack = navActionManager::goBack,
        snackbarHostState = snackbarHostState,
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            MoreItem(
                title = stringResource(UiRes.string.version),
                subtitle = stringResource(UiRes.string.app_version),
                icon = UiRes.drawable.ic_moelist_logo,
                onClick = {
                    if (versionClicks >= 7) {
                        scope.launch {
                            versionClicks = 0
                            snackbarHostState.showSnackbar("✧◝(⁰▿⁰)◜✧")
                        }
                    } else versionClicks++
                }
            )
            MoreItem(
                title = stringResource(UiRes.string.discord),
                subtitle = stringResource(UiRes.string.discord_summary),
                icon = UiRes.drawable.ic_discord,
                onClick = { uriHandler.openUri(DISCORD_SERVER_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.github),
                subtitle = stringResource(UiRes.string.github_summary),
                icon = UiRes.drawable.ic_github,
                onClick = { uriHandler.openUri(GITHUB_REPO_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.credits),
                subtitle = stringResource(UiRes.string.credits_summary),
                icon = UiRes.drawable.ic_round_group_24,
                onClick = dropUnlessResumed { navActionManager.toCredits() }
            )
        }
    }
}

@Preview
@Composable
fun AboutPreview() {
    MoeListTheme {
        Surface {
            AboutView(
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}