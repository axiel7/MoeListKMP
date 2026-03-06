package com.axiel7.moelist.main

import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.ui.TabletMode
import com.axiel7.moelist.data.model.ui.ThemeStyle
import com.axiel7.moelist.ui.base.navigation.DeepLink
import com.axiel7.moelist.ui.base.state.UiState
import com.materialkolor.PaletteStyle

@Stable
data class MainUiState(
    val deepLink: DeepLink<*>? = null,
    val theme: ThemeStyle = ThemeStyle.FOLLOW_SYSTEM,
    val useBlackColors: Boolean = false,
    val paletteStyle: PaletteStyle = PaletteStyle.Expressive,
    val accessToken: String? = null,
    val useListTabs: Boolean = false,
    val profilePicture: String? = null,
    val pinnedNavBar: Boolean = true,
    val tabletMode: TabletMode = TabletMode.AUTO,
    override val isLoading: Boolean = false,
    override val message: String? = null,
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
}
