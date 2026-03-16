package com.axiel7.moelist.ui.base

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf

@Stable
interface BrowserHandler {
    fun launchUrl(url: String)
    
    companion object {
        val LocalBrowserHandler = staticCompositionLocalOf<BrowserHandler> {
            error("CompositionLocal BrowserHandler not present")
        }
    }
}