package com.axiel7.moelist.screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.utils.MAL_ANNOUNCEMENTS_URL
import com.axiel7.moelist.data.utils.MAL_NEWS_URL
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.collapsable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.about
import com.axiel7.moelist.ui.generated.resources.anime_manga_news
import com.axiel7.moelist.ui.generated.resources.app_name
import com.axiel7.moelist.ui.generated.resources.feedback
import com.axiel7.moelist.ui.generated.resources.ic_campaign
import com.axiel7.moelist.ui.generated.resources.ic_info
import com.axiel7.moelist.ui.generated.resources.ic_moelist_logo
import com.axiel7.moelist.ui.generated.resources.ic_new_releases
import com.axiel7.moelist.ui.generated.resources.ic_round_feedback_24
import com.axiel7.moelist.ui.generated.resources.ic_round_power_settings_new_24
import com.axiel7.moelist.ui.generated.resources.ic_round_settings_24
import com.axiel7.moelist.ui.generated.resources.logout
import com.axiel7.moelist.ui.generated.resources.logout_summary
import com.axiel7.moelist.ui.generated.resources.mal_announcements
import com.axiel7.moelist.ui.generated.resources.mal_announcements_summary
import com.axiel7.moelist.ui.generated.resources.news_summary
import com.axiel7.moelist.ui.generated.resources.settings
import com.axiel7.moelist.screens.more.composables.FeedbackDialog
import com.axiel7.moelist.screens.more.composables.LogOutDialog
import com.axiel7.moelist.screens.more.composables.MoreItem
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoreView(
    isLoggedIn: Boolean,
    navActionManager: NavActionManager,
    topBarHeightPx: Float,
    topBarOffsetY: Float,
    topBarAnimateTo: suspend (Float) -> Unit,
    topBarSnapTo: suspend (Float) -> Unit,
    padding: PaddingValues,
) {
    val viewModel: MoreViewModel = koinViewModel()

    MoreViewContent(
        event = viewModel,
        navActionManager = navActionManager,
        topBarHeightPx = topBarHeightPx,
        topBarOffsetY = topBarOffsetY,
        topBarAnimateTo = topBarAnimateTo,
        topBarSnapTo = topBarSnapTo,
        padding = padding,
        isLoggedIn = isLoggedIn
    )
}

@Composable
private fun MoreViewContent(
    isLoggedIn: Boolean,
    event: MoreEvent?,
    navActionManager: NavActionManager,
    topBarHeightPx: Float = 0f,
    topBarOffsetY: Float = 0f,
    topBarAnimateTo: suspend (Float) -> Unit = {},
    topBarSnapTo: suspend (Float) -> Unit = {},
    padding: PaddingValues = PaddingValues(),
) {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    var openFeedbackDialog by remember { mutableStateOf(false) }
    var openLogOutDialog by remember { mutableStateOf(false) }

    if (openFeedbackDialog) {
        FeedbackDialog(
            onDismiss = { openFeedbackDialog = false }
        )
    } else if (openLogOutDialog) {
        LogOutDialog(
            onAccept = {
                event?.logOut()
                openLogOutDialog = false
            },
            onDismiss = { openLogOutDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Icon(
            painter = painterResource(UiRes.drawable.ic_moelist_logo),
            contentDescription = stringResource(UiRes.string.app_name),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
                .height(60.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )

        HorizontalDivider()

        MoreItem(
            title = stringResource(UiRes.string.anime_manga_news),
            subtitle = stringResource(UiRes.string.news_summary),
            icon = UiRes.drawable.ic_new_releases,
            onClick = {
                //TODO context.openCustomTab(MAL_NEWS_URL)
                uriHandler.openUri(MAL_NEWS_URL)
            }
        )

        MoreItem(
            title = stringResource(UiRes.string.mal_announcements),
            subtitle = stringResource(UiRes.string.mal_announcements_summary),
            icon = UiRes.drawable.ic_campaign,
            onClick = {
                //TODO context.openCustomTab(MAL_ANNOUNCEMENTS_URL)
                uriHandler.openUri(MAL_ANNOUNCEMENTS_URL)
            }
        )

        HorizontalDivider()

        // TODO: notifications
        /*MoreItem(
            title = stringResource(UiRes.string.notifications),
            icon = UiRes.drawable.round_notifications_24,
            onClick = dropUnlessResumed { navActionManager.toNotifications() }
        )*/

        MoreItem(
            title = stringResource(UiRes.string.settings),
            icon = UiRes.drawable.ic_round_settings_24,
            onClick = dropUnlessResumed { navActionManager.toSettings() }
        )

        MoreItem(
            title = stringResource(UiRes.string.about),
            icon = UiRes.drawable.ic_info,
            onClick = dropUnlessResumed { navActionManager.toAbout() }
        )

        MoreItem(
            title = stringResource(UiRes.string.feedback),
            icon = UiRes.drawable.ic_round_feedback_24,
            onClick = {
                openFeedbackDialog = true
            }
        )

        if (isLoggedIn) {
            HorizontalDivider()

            MoreItem(
                title = stringResource(UiRes.string.logout),
                subtitle = stringResource(UiRes.string.logout_summary),
                icon = UiRes.drawable.ic_round_power_settings_new_24,
                onClick = {
                    openLogOutDialog = true
                }
            )
        }
    }
}

@Preview
@Composable
fun MorePreview() {
    MoeListTheme {
        Surface {
            MoreViewContent(
                event = null,
                navActionManager = NavActionManager.rememberNavActionManager(),
                isLoggedIn = true
            )
        }
    }
}
