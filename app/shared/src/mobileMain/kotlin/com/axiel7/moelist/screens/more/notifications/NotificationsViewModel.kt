package com.axiel7.moelist.screens.more.notifications

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import com.axiel7.moelist.worker.NotificationWorkerManager
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(
    dataStore: DataStore<Preferences>,
    private val notificationWorkerManager: NotificationWorkerManager,
    private val scheduler: BackgroundTaskScheduler,
) : BaseViewModel<NotificationsUiState>(), NotificationsEvent {

    override val mutableUiState = MutableStateFlow(NotificationsUiState())

    override fun removeNotification(animeId: Int) {
        viewModelScope.launch {
            notificationWorkerManager.removeAiringAnimeNotification(animeId, scheduler)
        }
    }

    override fun removeAllNotifications() {
        viewModelScope.launch {
            notificationWorkerManager.removeAllNotifications(scheduler)
        }
    }

    init {
        dataStore.data
            .onEach { notifications ->
                mutableUiState.update { it.copy(notifications = notifications) }
            }
            .launchIn(viewModelScope)
    }
}