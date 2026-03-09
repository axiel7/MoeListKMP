package com.axiel7.moelist.ui.composables.button

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import java.awt.datatransfer.StringSelection

@OptIn(ExperimentalComposeUiApi::class)
actual fun String.toClipEntry(): ClipEntry {
    return ClipEntry(StringSelection(this))
}