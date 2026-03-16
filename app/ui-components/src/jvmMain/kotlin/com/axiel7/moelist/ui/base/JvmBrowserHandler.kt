package com.axiel7.moelist.ui.base

import androidx.compose.ui.platform.LocalUriHandler
import java.awt.Desktop
import java.net.URI

class JvmBrowserHandler: BrowserHandler {
    override fun launchUrl(url: String) {
        LocalUriHandler
        val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI(url))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}