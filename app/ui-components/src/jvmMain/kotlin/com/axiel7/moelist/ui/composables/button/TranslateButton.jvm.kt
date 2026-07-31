package com.axiel7.moelist.ui.composables.button

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.intl.Locale
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_outline_translate_24
import com.axiel7.moelist.ui.generated.resources.translate
import io.ktor.http.encodeURLParameter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// TODO: better implementation: option to choose default web translator?
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun TranslateButton(
    textToTranslate: String,
    modifier: Modifier
) {
    val uriHandler = LocalUriHandler.current
    val currentLanguage = remember { Locale.current.toLanguageTag() }
    IconButton(
        onClick = {
            uriHandler.openUri(
                buildGoogleTranslateUri(
                    text = textToTranslate,
                    targetLanguage = currentLanguage
                )
            )
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

private fun buildGoogleTranslateUri(
    text: String,
    targetLanguage: String,
) = "https://translate.google.com/?sl=en&tl=${targetLanguage}&text=${
    text.encodeURLParameter(spaceToPlus = true)
}&op=translate"