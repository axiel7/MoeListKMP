package com.axiel7.moelist.screens.details.topbar

import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.media.BaseMediaDetails
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.ui.base.state.UiState
import org.jetbrains.compose.resources.StringResource

@Stable
data class MediaNotificationsUiState(
    val mediaDetails: BaseMediaDetails? = null,
    val notification: String? = null,
    val startNotification: String? = null,
    val messageResource: StringResource? = null,
    override val isLoading: Boolean = true,
    override val message: String? = null,
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)

    val savedForNotification = when (mediaDetails?.status) {
        MediaStatus.AIRING -> notification
        MediaStatus.NOT_AIRED -> startNotification
        else -> null
    }
}
