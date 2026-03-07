package com.axiel7.moelist.screens.home

import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.model.anime.AnimeRanking
import com.axiel7.moelist.data.model.media.MediaSort
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.data.model.media.RankingType
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.data.repository.DefaultPreferencesRepository
import com.axiel7.moelist.data.utils.SeasonCalendar
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val animeRepository: AnimeRepository,
    defaultPreferencesRepository: DefaultPreferencesRepository,
) : BaseViewModel<HomeUiState>(), HomeEvent {

    override val mutableUiState = MutableStateFlow(HomeUiState())

    override fun initRequestChain(isLoggedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableUiState.update { it.copy(isLoading = true) }
            mutableUiState.value.run {
                if (todayAnimes.isEmpty()) getTodayAiringAnimes()
                if (seasonAnimes.isEmpty()) getSeasonAnimes()
                if (isLoggedIn && recommendedAnimes.isEmpty()) getRecommendedAnimes()
            }
            mutableUiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun getTodayAiringAnimes() {
        val result = animeRepository.getAnimeRanking(
            rankingType = RankingType.AIRING,
            limit = 100,
            fields = AnimeRepository.TODAY_FIELDS
        )
        if (result.data != null) {
            val tempList = mutableListOf<AnimeRanking>()
            for (anime in result.data) {
                if (anime.node.broadcast != null
                    && !tempList.contains(anime)
                    && anime.node.broadcast!!.dayOfTheWeek == SeasonCalendar.currentJapanWeekday
                    && anime.node.status == MediaStatus.AIRING
                ) {
                    tempList.add(anime)
                }
            }
            tempList.sortByDescending { it.node.broadcast?.startTime }
            mutableUiState.update { it.copy(todayAnimes = tempList) }
        } else {
            showMessage(result.message ?: result.error)
        }
    }

    private suspend fun getSeasonAnimes() {
        val currentStartSeason = SeasonCalendar.currentStartSeason
        val result = animeRepository.getSeasonalAnimes(
            sort = MediaSort.SCORE,
            startSeason = currentStartSeason,
            limit = 25,
            fields = "alternative_titles{en,ja},mean,my_list_status{status}",
        )
        result.data?.let { data -> mutableUiState.update { it.copy(seasonAnimes = data) } }
            ?: showMessage(result.message ?: result.error)
    }

    private suspend fun getRecommendedAnimes() {
        val result = animeRepository.getRecommendedAnimes(
            limit = 25
        )
        result.data?.let { data -> mutableUiState.update { it.copy(recommendedAnimes = data) } }
            ?: showMessage(result.message ?: result.error)
    }

    init {
        defaultPreferencesRepository.titleLang
            .onEach { value ->
                mutableUiState.update { it.copy(preferredTitle = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.hideScores
            .onEach { value ->
                mutableUiState.update { it.copy(hideScore = value) }
            }
            .launchIn(viewModelScope)
    }
}