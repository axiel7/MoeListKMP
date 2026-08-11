package com.axiel7.moelist.screens.details

import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.model.anime.AnimeDetails
import com.axiel7.moelist.data.model.anime.MyAnimeListStatus
import com.axiel7.moelist.data.model.anime.Recommendations
import com.axiel7.moelist.data.model.manga.MangaDetails
import com.axiel7.moelist.data.model.manga.MyMangaListStatus
import com.axiel7.moelist.data.model.media.BaseMediaNode
import com.axiel7.moelist.data.model.media.BaseMyListStatus
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.data.repository.DefaultPreferencesRepository
import com.axiel7.moelist.data.repository.MangaRepository
import com.axiel7.moelist.ui.base.navigation.Route
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MediaDetailsViewModel(
    arguments: Route.MediaDetails,
    defaultPreferencesRepository: DefaultPreferencesRepository,
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository,
) : BaseViewModel<MediaDetailsUiState>(), MediaDetailsEvent {
    private val mediaType = arguments.mediaType
    private val mediaId = arguments.mediaId

    override val mutableUiState = MutableStateFlow(MediaDetailsUiState())

    override fun onChangedMyListStatus(value: BaseMyListStatus?, removed: Boolean) {
        mutableUiState.update {
            when (it.mediaDetails) {
                is AnimeDetails -> {
                    it.copy(
                        mediaDetails = it.mediaDetails.copy(
                            myListStatus = (value as? MyAnimeListStatus).takeIf { !removed }
                        )
                    )
                }

                is MangaDetails -> {
                    it.copy(
                        mediaDetails = it.mediaDetails.copy(
                            myListStatus = (value as? MyMangaListStatus).takeIf { !removed }
                        )
                    )
                }

                else -> it
            }
        }
    }

    override fun getCharacters() {
        viewModelScope.launch {
            mutableUiState.update { it.copy(isLoadingCharacters = true) }

            val result = animeRepository.getAnimeCharacters(
                animeId = mediaId,
                limit = 40,
                offset = null,
                page = null,
            )

            if (result.wasError) {
                mutableUiState.update {
                    it.copy(
                        isLoadingCharacters = false,
                        message = result.message ?: "Error loading characters"
                    )
                }
            } else {
                mutableUiState.update {
                    it.copy(
                        characters = result.data.orEmpty().sortedBy { it.role },
                        isLoadingCharacters = false
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            setLoading(true)
            val mediaDetails = if (mediaType == MediaType.ANIME) {
                animeRepository.getAnimeDetails(mediaId)
            } else {
                mangaRepository.getMangaDetails(mediaId)
            }

            if (mediaDetails == null) showMessage("Unable to reach server")
            else if (mediaDetails.error != null) showMessage(mediaDetails.error)
            else {
                val recommendations =
                    (mediaDetails.recommendations as? List<Recommendations<BaseMediaNode>>).orEmpty()

                val picturesUrls = listOf(mediaDetails.mainPicture?.large.orEmpty())
                    .plus(mediaDetails.pictures?.map { it.large ?: it.medium.orEmpty() }
                        .orEmpty())

                mutableUiState.update {
                    it.copy(
                        mediaDetails = mediaDetails,
                        relatedAnime = mediaDetails.relatedAnime.orEmpty(),
                        relatedManga = mediaDetails.relatedManga.orEmpty(),
                        recommendations = recommendations,
                        picturesUrls = picturesUrls,
                        isLoading = false
                    )
                }

                if (mediaType == MediaType.ANIME
                    && defaultPreferencesRepository.loadCharacters.first()
                ) {
                    getCharacters()
                }
            }
        }

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
