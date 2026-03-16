package com.axiel7.moelist.screens.details.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.screens.details.MediaDetailsEvent
import com.axiel7.moelist.screens.details.MediaDetailsUiState
import com.axiel7.moelist.ui.composables.button.BackIconButton
import com.axiel7.moelist.ui.composables.button.ShareButton
import com.axiel7.moelist.ui.composables.button.ViewInBrowserButton

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun MediaDetailsTopAppBar(
    uiState: MediaDetailsUiState,
    event: MediaDetailsEvent?,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateBack: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    /*val savedForNotification = when (uiState.mediaDetails?.status) {
        MediaStatus.AIRING -> uiState.notification
        MediaStatus.NOT_AIRED -> uiState.startNotification
        else -> null
    }

    fun onClickNotification(permissionGranted: Boolean) {
        val enable = savedForNotification == null
        (uiState.mediaDetails as? AnimeDetails)?.let { details ->
            if (enable && permissionGranted) {
                if (details.status != MediaStatus.NOT_AIRED
                    && details.broadcast?.dayOfTheWeek != null
                    && details.broadcast?.startTime != null
                ) {
                    event?.scheduleAiringAnimeNotification(
                        title = details.title.orEmpty(),
                        animeId = details.id,
                        weekDay = details.broadcast!!.dayOfTheWeek!!,
                        jpHour = LocalTime.parse(details.broadcast!!.startTime!!)
                    )
                    //TODO context.showToast(UiRes.string.airing_notification_enabled)
                } else if (details.status == MediaStatus.NOT_AIRED && details.startDate != null) {
                    val startDate = details.startDate?.parseDate()
                    if (startDate != null) {
                        event?.scheduleAnimeStartNotification(
                            title = details.title.orEmpty(),
                            animeId = details.id,
                            startDate = startDate
                        )
                        //TODO context.showToast(UiRes.string.start_airing_notification_enabled)
                    } else {
                        //TODO context.showToast(UiRes.string.invalid_start_date)
                    }
                } else {
                    if (details.broadcast?.dayOfTheWeek == null
                        || details.broadcast?.startTime == null
                    ) {
                        //TODO context.showToast(UiRes.string.invalid_broadcast)
                    } else if (details.startDate == null) {
                        //TODO context.showToast(UiRes.string.invalid_start_date)
                    }
                }
            } else {
                event?.removeAiringAnimeNotification(animeId = details.id)
                //TODO context.showToast("Notification disabled")
            }
        }
    }

    //TODO
    val notificationPermission = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPreview) {
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            onPermissionResult = { onClickNotification(it) }
        )
    } else null*/

    TopAppBar(
        title = { },
        navigationIcon = {
            BackIconButton(onClick = navigateBack)
        },
        actions = {
            /*if (uiState.mediaDetails?.status == MediaStatus.AIRING
                || uiState.mediaDetails?.status == MediaStatus.NOT_AIRED
            ) {
                IconButton(
                    onClick = {
                        /*if (notificationPermission == null || notificationPermission.status.isGranted) {
                            onClickNotification(true)
                        } else {
                            notificationPermission.launchPermissionRequest()
                        }*/
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
            }*/
            ViewInBrowserButton(onClick = {
                uiState.mediaDetails?.malUrl?.let { uriHandler.openUri(it) }
            })

            ShareButton(url = uiState.mediaDetails?.malUrl.orEmpty())
        },
        scrollBehavior = scrollBehavior
    )
}