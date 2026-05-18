package com.axiel7.moelist.screens.details.topbar

import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.model.anime.AnimeDetails
import com.axiel7.moelist.data.model.media.BaseMediaDetails
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.data.utils.DateUtils.parseDate
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.airing_notification_enabled
import com.axiel7.moelist.ui.generated.resources.invalid_broadcast
import com.axiel7.moelist.ui.generated.resources.invalid_start_date
import com.axiel7.moelist.ui.generated.resources.start_airing_notification_enabled
import com.axiel7.moelist.worker.NotificationWorkerManager
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource

class MediaNotificationsViewModel(
    private val notificationWorkerManager: NotificationWorkerManager,
    private val scheduler: BackgroundTaskScheduler,
) : BaseViewModel<MediaNotificationsUiState>() {

    override val mutableUiState = MutableStateFlow(MediaNotificationsUiState())

    override fun onMessageDisplayed() {
        mutableUiState.update { it.copy(messageResource = null, message = null) }
    }

    fun showMessage(res: StringResource) {
        mutableUiState.update { it.copy(messageResource = res) }
    }

    fun onClickNotification(permissionGranted: Boolean) {
        viewModelScope.launch {
            val enable = uiState.value.savedForNotification == null
            (uiState.value.mediaDetails as? AnimeDetails)?.let { details ->
                if (enable && permissionGranted) {
                    if (details.status != MediaStatus.NOT_AIRED
                        && details.broadcast?.dayOfTheWeek != null
                        && details.broadcast?.startTime != null
                    ) {
                        notificationWorkerManager.scheduleAiringAnimeNotification(
                            title = details.title.orEmpty(),
                            animeId = details.id,
                            weekDay = details.broadcast!!.dayOfTheWeek!!,
                            jpHour = LocalTime.parse(details.broadcast!!.startTime!!),
                            scheduler = scheduler,
                        )
                        showMessage(UiRes.string.airing_notification_enabled)
                    } else if (details.status == MediaStatus.NOT_AIRED && details.startDate != null) {
                        val startDate = details.startDate?.parseDate()
                        if (startDate != null) {
                            notificationWorkerManager.scheduleAnimeStartNotification(
                                title = details.title.orEmpty(),
                                animeId = details.id,
                                startDate = startDate,
                                scheduler = scheduler,
                            )
                            showMessage(UiRes.string.start_airing_notification_enabled)
                        } else {
                            showMessage(UiRes.string.invalid_start_date)
                        }
                    } else {
                        if (details.broadcast?.dayOfTheWeek == null
                            || details.broadcast?.startTime == null
                        ) {
                            showMessage(UiRes.string.invalid_broadcast)
                        } else if (details.startDate == null) {
                            showMessage(UiRes.string.invalid_start_date)
                        }
                    }
                } else {
                    notificationWorkerManager.removeAiringAnimeNotification(
                        animeId = details.id,
                        scheduler = scheduler,
                    )
                    showMessage("Notification disabled")
                }
            }
        }
    }

    fun init(mediaDetails: BaseMediaDetails) {
        mutableUiState.update { it.copy(mediaDetails = mediaDetails) }
        notificationWorkerManager.getNotification(mediaDetails.id)
            .onEach { notification ->
                mutableUiState.update { it.copy(notification = notification) }
            }
            .launchIn(viewModelScope)

        notificationWorkerManager.getStartNotification(mediaDetails.id)
            .onEach { startNotification ->
                mutableUiState.update { it.copy(startNotification = startNotification) }
            }
            .launchIn(viewModelScope)
    }
}