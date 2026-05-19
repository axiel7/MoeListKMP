package com.axiel7.moelist.screens.details.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axiel7.moelist.data.model.media.BaseMediaDetails
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.ui.composables.button.BackIconButton
import com.axiel7.moelist.ui.composables.button.ShareButton
import com.axiel7.moelist.ui.composables.button.ViewInBrowserButton
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.round_notifications_active_24
import com.axiel7.moelist.ui.generated.resources.round_notifications_off_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun MediaDetailsTopAppBar(
    mediaDetails: BaseMediaDetails?,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    if (isPreview) {
        MediaDetailsTopAppBarContent(
            mediaDetails = mediaDetails,
            savedForNotification = null,
            scrollBehavior = null,
            navigateBack = navigateBack,
            onClickNotification = { }
        )
        return
    }
    val viewModel: MediaNotificationsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(mediaDetails) {
        mediaDetails?.let { viewModel.init(it) }
    }

    val message = uiState.messageResource?.let { stringResource(it) } ?: uiState.message

    LaunchedEffect(message) {
        message?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onMessageDisplayed()
        }
    }

    MediaDetailsTopAppBarContent(
        mediaDetails = mediaDetails,
        savedForNotification = uiState.savedForNotification,
        scrollBehavior = scrollBehavior,
        navigateBack = navigateBack,
        onClickNotification = viewModel::onClickNotification,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MediaDetailsTopAppBarContent(
    mediaDetails: BaseMediaDetails?,
    savedForNotification: String?,
    scrollBehavior: TopAppBarScrollBehavior?,
    navigateBack: () -> Unit,
    onClickNotification: (Boolean) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    fun requestPermission() {
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
            options = UNAuthorizationOptionSound or UNAuthorizationOptionBadge or UNAuthorizationOptionAlert
        ) { granted, _ ->
            onClickNotification(granted)
        }
    }

    TopAppBar(
        title = { },
        navigationIcon = {
            BackIconButton(onClick = navigateBack)
        },
        actions = {
            if ((mediaDetails?.status == MediaStatus.AIRING
                        || mediaDetails?.status == MediaStatus.NOT_AIRED)
            ) {
                IconButton(
                    onClick = { requestPermission() },
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        painter = painterResource(
                            if (savedForNotification != null) UiRes.drawable.round_notifications_active_24
                            else UiRes.drawable.round_notifications_off_24
                        ),
                        contentDescription = "notification"
                    )
                }
            }
            ViewInBrowserButton(onClick = {
                mediaDetails?.malUrl?.let { uriHandler.openUri(it) }
            })

            ShareButton(url = mediaDetails?.malUrl.orEmpty())
        },
        scrollBehavior = scrollBehavior
    )
}