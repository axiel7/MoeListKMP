package com.axiel7.moelist.screens.details.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.axiel7.moelist.data.model.media.BaseMediaDetails
import com.axiel7.moelist.ui.composables.button.BackIconButton
import com.axiel7.moelist.ui.composables.button.ShareButton
import com.axiel7.moelist.ui.composables.button.ViewInBrowserButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun MediaDetailsTopAppBar(
    mediaDetails: BaseMediaDetails?,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    TopAppBar(
        title = { },
        navigationIcon = {
            BackIconButton(onClick = navigateBack)
        },
        actions = {
            ViewInBrowserButton(onClick = {
                mediaDetails?.malUrl?.let { uriHandler.openUri(it) }
            })

            ShareButton(url = mediaDetails?.malUrl.orEmpty())
        },
        scrollBehavior = scrollBehavior
    )
}