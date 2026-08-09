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
import com.axiel7.moelist.ui.base.event.UiEvent
import com.materialkolor.PaletteStyle

@Stable
interface SettingsEvent : UiEvent {
    fun setLanguage(value: AppLanguage)
    fun setTheme(value: ThemeStyle)
    fun setUseCustomAppColor(value: Boolean)
    fun setCustomAppColor(value: Color)
    fun setUseBlackColors(value: Boolean)
    fun setPaletteStyle(value: PaletteStyle)
    fun setShowNsfw(value: Boolean)
    fun setHideScores(value: Boolean)
    fun setUseGeneralListStyle(value: Boolean)
    fun setGeneralListStyle(value: ListStyle)
    fun setItemsPerRow(value: ItemsPerRow)
    fun setStartTab(value: StartTab)
    fun setTabletMode(value: TabletMode)
    fun setPinnedNavBar(value: Boolean)
    fun setTitleLanguage(value: TitleLanguage)
    fun setUseListTabs(value: Boolean)
    fun setLoadCharacters(value: Boolean)
    fun setRandomListEntryEnabled(value: Boolean)
}