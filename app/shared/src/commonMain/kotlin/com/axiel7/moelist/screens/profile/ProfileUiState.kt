package com.axiel7.moelist.screens.profile

import androidx.compose.runtime.Immutable
import com.axiel7.moelist.data.model.User
import com.axiel7.moelist.data.model.UserStats
import com.axiel7.moelist.ui.base.model.ListStatus
import com.axiel7.moelist.ui.base.model.Stat
import com.axiel7.moelist.ui.base.state.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ProfileUiState(
    val user: User? = null,
    val profilePictureUrl: String? = null,
    val animeStats: ImmutableList<Stat<ListStatus>> = persistentListOf(),
    val mangaStats: ImmutableList<Stat<ListStatus>> = persistentListOf(),
    val userMangaStats: UserStats.MangaStats? = null,
    val isLoadingManga: Boolean = true,
    override val isLoading: Boolean = true,
    override val message: String? = null
) : UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
}
