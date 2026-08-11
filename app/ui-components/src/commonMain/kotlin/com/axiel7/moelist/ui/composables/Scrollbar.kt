package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformHorizontalScrollbar(scrollState: LazyListState)

@Composable
expect fun PlatformVerticalScrollbar(scrollState: LazyListState, modifier: Modifier)

@Composable
expect fun PlatformVerticalScrollbar(scrollState: LazyGridState, modifier: Modifier)