package com.axiel7.moelist.screens.details.topbar

import android.Manifest
import android.os.Build
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalPermissionsApi::class
)
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
            notificationPermission = null,
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

    fun onClickNotification(permissionGranted: Boolean) {
        viewModel.onClickNotification(permissionGranted = permissionGranted)
    }

    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            onPermissionResult = ::onClickNotification,
        )
    } else null

    MediaDetailsTopAppBarContent(
        mediaDetails = mediaDetails,
        savedForNotification = uiState.savedForNotification,
        scrollBehavior = scrollBehavior,
        notificationPermission = notificationPermission,
        navigateBack = navigateBack,
        onClickNotification = ::onClickNotification,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
private fun MediaDetailsTopAppBarContent(
    mediaDetails: BaseMediaDetails?,
    savedForNotification: String?,
    scrollBehavior: TopAppBarScrollBehavior?,
    notificationPermission: PermissionState?,
    navigateBack: () -> Unit,
    onClickNotification: (Boolean) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    TopAppBar(
        title = { },
        navigationIcon = {
            BackIconButton(onClick = navigateBack)
        },
        actions = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && (mediaDetails?.status == MediaStatus.AIRING
                        || mediaDetails?.status == MediaStatus.NOT_AIRED)
            ) {
                IconButton(
                    onClick = {
                        if (notificationPermission == null || notificationPermission.status.isGranted) {
                            onClickNotification(true)
                        } else {
                            notificationPermission.launchPermissionRequest()
                        }
                    },
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