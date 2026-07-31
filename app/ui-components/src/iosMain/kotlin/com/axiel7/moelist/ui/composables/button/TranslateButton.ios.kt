package com.axiel7.moelist.ui.composables.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_outline_translate_24
import com.axiel7.moelist.ui.generated.resources.translate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun TranslateButton(
    textToTranslate: String,
    modifier: Modifier
) {
    val translationBridge = LocalTranslationBridge.current
    IconButton(
        onClick = {
            translationBridge.requestTranslation(textToTranslate)
        },
        shapes = IconButtonDefaults.shapes(),
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.ic_outline_translate_24),
            contentDescription = stringResource(UiRes.string.translate)
        )
    }
}

class TranslationBridge {
    var onTranslateRequested: ((String) -> Unit)? = null

    fun requestTranslation(text: String) {
        onTranslateRequested?.invoke(text)
    }
}

val LocalTranslationBridge = compositionLocalOf { TranslationBridge() }