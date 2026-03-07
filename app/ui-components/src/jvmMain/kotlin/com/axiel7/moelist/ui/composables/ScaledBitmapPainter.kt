package com.axiel7.moelist.ui.composables

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.fastRoundToInt
import coil3.toBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.FilterMipmap
import org.jetbrains.skia.FilterMode
import org.jetbrains.skia.MipmapMode
import org.jetbrains.skia.Image as SkiaImage

// https://youtrack.jetbrains.com/issue/CMP-9492
class ScaledBitmapPainter(
    val image: coil3.Image,
    val filterQuality: FilterQuality = FilterQuality.Low
) : Painter() {
    override val intrinsicSize: Size
        get() = Size(image.width.toFloat(), image.height.toFloat())

    override fun DrawScope.onDraw() {
        val size = IntSize(
            size.width.fastRoundToInt(),
            size.height.fastRoundToInt(),
        )
        val bitmap = SkiaImage.makeFromBitmap(
            image.toBitmap().asComposeImageBitmap().asSkiaBitmap()
        ).scale(size.width, size.height)

        drawImage(
            bitmap,
            IntOffset.Zero,
            size,
            dstSize = size,
            alpha = 1.0f,
            colorFilter = null,
            filterQuality = filterQuality,
        )
    }

    fun SkiaImage.scale(width: Int, height: Int): ImageBitmap {
        val bitmap = Bitmap()
        bitmap.allocN32Pixels(width, height)
        scalePixels(bitmap.peekPixels()!!, FilterMipmap(FilterMode.LINEAR, MipmapMode.LINEAR), false)
        return SkiaImage.makeFromBitmap(bitmap).toComposeImageBitmap()
    }
}