package com.axiel7.moelist.screens.calendar

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.base.event.UiEvent

@Stable
interface CalendarEvent : UiEvent {
    fun onMyListChanged(value: Boolean?)
}