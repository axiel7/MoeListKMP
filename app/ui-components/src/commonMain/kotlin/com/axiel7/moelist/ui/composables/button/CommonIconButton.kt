package com.axiel7.moelist.ui.composables.button

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_arrow_back
import com.axiel7.moelist.ui.generated.resources.ic_open_in_browser
import com.axiel7.moelist.ui.generated.resources.share
import com.axiel7.moelist.ui.generated.resources.view_on_mal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BackIconButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = dropUnlessResumed { onClick() },
        shapes = IconButtonDefaults.shapes()
    ) {
        Icon(painter = painterResource(UiRes.drawable.ic_arrow_back), contentDescription = "arrow_back")
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ViewInBrowserButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = dropUnlessResumed { onClick() },
        shapes = IconButtonDefaults.shapes()
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.ic_open_in_browser),
            contentDescription = stringResource(UiRes.string.view_on_mal)
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
expect fun ShareButton(
    url: String,
    shapes: IconButtonShapes = IconButtonDefaults.shapes(),
    contentDescription: String = stringResource(UiRes.string.share)
)

@Composable
expect fun OpenByDefaultSettingsButton()