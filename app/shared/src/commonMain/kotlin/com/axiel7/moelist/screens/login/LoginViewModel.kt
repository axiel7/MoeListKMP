package com.axiel7.moelist.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.PlatformType
import com.axiel7.moelist.data.network.OAuthService
import com.axiel7.moelist.data.repository.LoginRepository.Companion.codeVerifier
import com.axiel7.moelist.data.utils.CLIENT_ID
import com.axiel7.moelist.data.utils.MAL_OAUTH2_URL
import com.axiel7.moelist.data.utils.MOELIST_OAUTH_STATE
import com.axiel7.moelist.getPlatform
import com.axiel7.moelist.ui.base.BrowserHandler
import kotlinx.coroutines.launch

class LoginViewModel(
    private val oAuthService: OAuthService,
) : ViewModel(), LoginEvent {

    val androidLoginUrl =
        "${MAL_OAUTH2_URL}authorize?response_type=code&client_id=${CLIENT_ID}&code_challenge=${codeVerifier}&state=${MOELIST_OAUTH_STATE}"

    override fun openLogin(browserHandler: BrowserHandler) {
        viewModelScope.launch {
            // https://github.com/kalinjul/kotlin-multiplatform-oidc/issues/170
            if (getPlatform().type == PlatformType.ANDROID) {
                browserHandler.launchUrl(androidLoginUrl)
            } else {
                oAuthService.login()
            }
        }
    }
}