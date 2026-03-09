package com.axiel7.moelist.ui.composables.button

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.runtime.Composable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ios_share_24
import org.jetbrains.compose.resources.painterResource
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.popoverPresentationController

@OptIn(markerClass = [ExperimentalMaterial3ExpressiveApi::class])
@Composable
actual fun ShareButton(
    url: String,
    shapes: IconButtonShapes,
    contentDescription: String
) {
    IconButton(
        onClick = { shareLink(url) },
        shapes = shapes
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.ios_share_24),
            contentDescription = contentDescription,
        )
    }
}

private fun shareLink(url: String) {
    val activityViewController = UIActivityViewController(
        activityItems = listOf(url),
        applicationActivities = null
    )

    // Find the current Window and RootViewController to present the share sheet
    val window = UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
    val rootViewController = window?.rootViewController

    // Crucial for iPad support: avoid crash by providing a sourceView
    activityViewController.popoverPresentationController?.sourceView = rootViewController?.view

    rootViewController?.presentViewController(
        viewControllerToPresent = activityViewController,
        animated = true,
        completion = null
    )
}