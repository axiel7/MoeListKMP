package com.axiel7.moelist.ui.composables.button

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import java.awt.datatransfer.StringSelection

@OptIn(markerClass = [ExperimentalMaterial3ExpressiveApi::class])
@Composable
actual fun ShareButton(
    url: String,
    shapes: IconButtonShapes,
    contentDescription: String
) {
    CopyButton(
        valueForCopy = url,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
suspend fun Clipboard.copyText(text: String) {
    setClipEntry(ClipEntry(StringSelection(text)))
}