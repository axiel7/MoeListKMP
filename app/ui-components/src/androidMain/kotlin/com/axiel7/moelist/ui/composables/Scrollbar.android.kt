package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformHorizontalScrollbar(scrollState: LazyListState) {}

@Composable
actual fun PlatformVerticalScrollbar(scrollState: LazyListState, modifier: Modifier) {}

@Composable
actual fun PlatformVerticalScrollbar(scrollState: LazyGridState, modifier: Modifier) {}