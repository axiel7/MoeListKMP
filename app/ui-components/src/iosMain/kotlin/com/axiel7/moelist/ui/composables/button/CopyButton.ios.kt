package com.axiel7.moelist.ui.composables.button

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

@OptIn(markerClass = [ExperimentalComposeUiApi::class])
actual fun String.toClipEntry(): ClipEntry {
    return ClipEntry.withPlainText(this)
}