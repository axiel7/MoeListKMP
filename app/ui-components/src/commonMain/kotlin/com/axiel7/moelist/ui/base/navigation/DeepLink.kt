package com.axiel7.moelist.ui.base.navigation

import androidx.compose.runtime.Stable

@Stable
data class DeepLink<T>(
    val type: Type,
    val data: T,
) {
    // We could have ANIME and MANGA combined, but MAL urls have the distinction.
    // In the end both of them should open the MediaDetailsView
    enum class Type {
        ANIME, MANGA, LOGIN, EXTERNAL
    }
}
