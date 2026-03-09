package com.axiel7.moelist.ui.composables.button

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.axiel7.moelist.ui.composables.preferences.PlainPreferenceView
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_open_in_browser
import com.axiel7.moelist.ui.generated.resources.open_mal_links_in_the_app
import com.axiel7.moelist.ui.generated.resources.round_share_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(markerClass = [ExperimentalMaterial3ExpressiveApi::class])
@Composable
actual fun ShareButton(
    url: String,
    shapes: IconButtonShapes,
    contentDescription: String
) {
    val context = LocalContext.current
    IconButton(
        onClick = { context.shareLink(url) },
        shapes = shapes
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.round_share_24),
            contentDescription = contentDescription,
        )
    }
}

@Composable
actual fun OpenByDefaultSettingsButton() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        PlainPreferenceView(
            title = stringResource(UiRes.string.open_mal_links_in_the_app),
            icon = UiRes.drawable.ic_open_in_browser,
            onClick = {
                context.openByDefaultSettings()
            }
        )
    }
}

private fun Context.shareLink(url: String) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
        startActivity(Intent.createChooser(this, null))
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun Context.openByDefaultSettings() {
    try {
        // Samsung OneUI 4 bug can't open ACTION_APP_OPEN_BY_DEFAULT_SETTINGS
        val action = if (Build.MANUFACTURER.equals("samsung", ignoreCase = true)) {
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        } else {
            Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS
        }
        Intent(
            action,
            "package:${packageName}".toUri()
        ).apply {
            startActivity(this)
        }
    } catch (e: Exception) {
        showToast(e.message ?: "Error")
    }
}

private fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}