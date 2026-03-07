package com.axiel7.moelist.main

import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.GlobalVariables
import com.axiel7.moelist.data.network.OAuthService
import com.axiel7.moelist.data.repository.DefaultPreferencesRepository
import com.axiel7.moelist.ui.base.navigation.DeepLink
import com.axiel7.moelist.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect

@OptIn(ExperimentalOpenIdConnect::class)
class MainViewModel(
    private val oAuthService: OAuthService,
    private val globalVariables: GlobalVariables,
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) : BaseViewModel<MainUiState>(), MainEvent {

    override val mutableUiState = MutableStateFlow(MainUiState())

    val startTab = defaultPreferencesRepository.startTab

    val lastTab = defaultPreferencesRepository.lastTab

    override fun saveLastTab(value: Int) {
        viewModelScope.launch {
            defaultPreferencesRepository.setLastTab(value)
        }
    }

    suspend fun initGlobalVariables() {
        defaultPreferencesRepository.titleLang.firstOrNull()?.let {
            globalVariables.titleLanguage = it
        }
    }

    fun continueAuthFlow() {
        viewModelScope.launch {
            if (oAuthService.canContinueLogin()) {
                oAuthService.continueLogin()
            }
        }
    }

    fun setDeepLink(deepLink: DeepLink<*>?) {
        mutableUiState.update { it.copy(deepLink = deepLink) }
    }

    init {
        oAuthService.tokenStore.accessTokenFlow
            .onEach { value ->
                mutableUiState.update { it.copy(isLoggedIn = value != null) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.lang
            .onEach { value ->
                mutableUiState.update { it.copy(language = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.theme
            .onEach { value ->
                mutableUiState.update { it.copy(theme = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.useBlackColors
            .onEach { value ->
                mutableUiState.update { it.copy(useBlackColors = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.paletteStyle
            .onEach { value ->
                mutableUiState.update { it.copy(paletteStyle = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.useListTabs
            .onEach { value ->
                mutableUiState.update { it.copy(useListTabs = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.profilePicture
            .onEach { value ->
                mutableUiState.update { it.copy(profilePicture = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.pinnedNavBar
            .onEach { value ->
                mutableUiState.update { it.copy(pinnedNavBar = value) }
            }
            .launchIn(viewModelScope)

        defaultPreferencesRepository.tabletMode
            .onEach { value ->
                mutableUiState.update { it.copy(tabletMode = value) }
            }
            .launchIn(viewModelScope)
    }
}
