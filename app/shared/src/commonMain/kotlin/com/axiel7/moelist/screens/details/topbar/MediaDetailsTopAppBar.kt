package com.axiel7.moelist.screens.details.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.axiel7.moelist.data.model.media.BaseMediaDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun MediaDetailsTopAppBar(
    mediaDetails: BaseMediaDetails?,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit
)