package com.axiel7.moelist.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axiel7.moelist.data.network.OAuthService
import com.axiel7.moelist.ui.base.BrowserHandler
import kotlinx.coroutines.launch

class LoginViewModel(
    private val oAuthService: OAuthService,
) : ViewModel(), LoginEvent {

    override fun openLogin(browserHandler: BrowserHandler) {
        viewModelScope.launch {
            runCatching {
                oAuthService.login()
            }
        }
    }

    fun continueLogin() {
        viewModelScope.launch {
            runCatching {
                if (oAuthService.canContinueLogin()) {
                    oAuthService.continueLogin()
                }
            }
        }
    }
}