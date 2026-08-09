package com.axiel7.moelist.screens.more.settings

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.axiel7.moelist.data.model.media.TitleLanguage
import com.axiel7.moelist.data.model.ui.AppLanguage
import com.axiel7.moelist.data.model.ui.ItemsPerRow
import com.axiel7.moelist.data.model.ui.ListStyle
import com.axiel7.moelist.data.model.ui.StartTab
import com.axiel7.moelist.data.model.ui.TabletMode
import com.axiel7.moelist.data.model.ui.ThemeStyle
import com.axiel7.moelist.ui.base.state.UiState
import com.materialkolor.PaletteStyle

@Stable
data class SettingsUiState(
    val language: AppLanguage = AppLanguage.FOLLOW_SYSTEM,
    val theme: ThemeStyle = ThemeStyle.FOLLOW_SYSTEM,
    val useCustomAppColor: Boolean = false,
    val customAppColor: Color? = null,
    val useBlackColors: Boolean = false,
    val paletteStyle: PaletteStyle = PaletteStyle.Expressive,
    val showNsfw: Boolean = false,
    val hideScores: Boolean = false,
    val useGeneralListStyle: Boolean = true,
    val generalListStyle: ListStyle = ListStyle.STANDARD,
    val itemsPerRow: ItemsPerRow = ItemsPerRow.DEFAULT,
    val startTab: StartTab = StartTab.LAST_USED,
    val tabletMode: TabletMode = TabletMode.AUTO,
    val pinnedNavBar: Boolean = false,
    val titleLanguage: TitleLanguage = TitleLanguage.ROMAJI,
    val useListTabs: Boolean = false,
    val loadCharacters: Boolean = false,
    val randomListEntryEnabled: Boolean = false,
    override val isLoading: Boolean = false,
    override val message: String? = null
) : UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
}
