package com.axiel7.moelist.ui.composables.button

import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.copied
import com.axiel7.moelist.ui.generated.resources.round_check_24
import com.axiel7.moelist.ui.generated.resources.round_content_copy_24
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CopyButton(
    valueForCopy: String,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current
    var wasCopied by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            scope.launch {
                clipboard.setClipEntry(valueForCopy.toClipEntry())
                wasCopied = true
            }
        },
        shapes = IconButtonDefaults.shapes(),
        modifier = modifier,
    ) {
        Crossfade(wasCopied) { wasCopied ->
            Icon(
                painter = painterResource(
                    resource = if (wasCopied) UiRes.drawable.round_check_24
                    else UiRes.drawable.round_content_copy_24
                ),
                contentDescription = stringResource(UiRes.string.copied),
            )
        }
    }

    LaunchedEffect(wasCopied) {
        if (wasCopied) {
            delay(2.seconds)
            wasCopied = false
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
expect fun String.toClipEntry(): ClipEntry
