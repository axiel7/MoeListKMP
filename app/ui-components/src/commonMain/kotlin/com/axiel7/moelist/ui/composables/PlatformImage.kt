package com.axiel7.moelist.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
expect fun PlatformImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    contentScale: ContentScale = ContentScale.Fit,
)