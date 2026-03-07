package com.axiel7.moelist.screens.calendar

import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.data.repository.DefaultPreferencesRepository
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalendarViewModel(
    defaultPreferencesRepository: DefaultPreferencesRepository,
    private val animeRepository: AnimeRepository
) : BaseViewModel<CalendarUiState>(), CalendarEvent {

    override val mutableUiState = MutableStateFlow(CalendarUiState(isLoading = true))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = animeRepository.getWeeklyAnime()

            if (result.wasError) {
                showMessage(result.message)
            }
            result.data?.let { data ->
                mutableUiState.update {
                    it.copy(
                        mondayAnime = data[0],
                        tuesdayAnime = data[1],
                        wednesdayAnime = data[2],
                        thursdayAnime = data[3],
                        fridayAnime = data[4],
                        saturdayAnime = data[5],
                        sundayAnime = data[6]
                    )
                }
            }
            setLoading(false)
        }

        defaultPreferencesRepository.titleLang
            .onEach { value ->
                mutableUiState.update { it.copy(preferredTitle = value) }
            }
            .launchIn(viewModelScope)
    }
}