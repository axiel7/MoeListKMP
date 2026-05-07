package com.axiel7.moelist.screens.login

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.base.BrowserHandler

@Stable
interface LoginEvent {
    fun openLogin(browserHandler: BrowserHandler)
}