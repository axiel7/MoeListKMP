package com.axiel7.moelist.screens.details

import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.media.BaseMyListStatus
import com.axiel7.moelist.ui.base.event.UiEvent

@Stable
interface MediaDetailsEvent : UiEvent {

    fun onChangedMyListStatus(value: BaseMyListStatus?, removed: Boolean = false)

    fun getCharacters()
}