package com.axiel7.moelist.ui.composables.button

import android.content.ClipData
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.toClipEntry

@OptIn(markerClass = [ExperimentalComposeUiApi::class])
actual fun String.toClipEntry(): ClipEntry {
    return ClipData.newPlainText(this, this).toClipEntry()
}