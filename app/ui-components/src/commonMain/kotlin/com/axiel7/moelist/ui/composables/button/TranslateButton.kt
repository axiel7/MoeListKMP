package com.axiel7.moelist.ui.composables.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun TranslateButton(
    textToTranslate: String,
    modifier: Modifier = Modifier
)