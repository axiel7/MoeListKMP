package com.axiel7.moelist.ui.base

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

class AndroidBrowserHandler(
    private val context: Context
): BrowserHandler {
    override fun launchUrl(url: String) {
        CustomTabsIntent.Builder()
            .build()
            .apply {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                try {
                    launchUrl(context, url.toUri())
                } catch (_: ActivityNotFoundException) {
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                    } catch (e: Exception) {
                        Toast.makeText(context, e.localizedMessage.orEmpty(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}