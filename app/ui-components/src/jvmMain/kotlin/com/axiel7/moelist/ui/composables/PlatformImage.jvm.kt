package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.BitmapImage
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.asPainter
import coil3.request.ErrorResult
import coil3.request.ImageRequest

// https://github.com/coil-kt/coil/issues/2883
@Composable
actual fun PlatformImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier,
    placeholder: Painter?,
    error: Painter?,
    fallback: Painter?,
    contentScale: ContentScale,
) {
    val context = LocalPlatformContext.current
    val imageState = remember { mutableStateOf(placeholder) }

    LaunchedEffect(model) {
        val request = ImageRequest.Builder(context)
            .data(model)
            .build()
        val loader = SingletonImageLoader.get(context)
        val result = loader.execute(request)
        if (result is ErrorResult) imageState.value = error
        else {
            val painter = when (val image = result.image) {
                is BitmapImage -> ScaledBitmapPainter(image)
                else -> image?.asPainter(context)
            }
            imageState.value = painter ?: fallback
        }
    }

    Image(
        painter = imageState.value ?: ColorPainter(Color.Unspecified),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}