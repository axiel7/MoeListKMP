package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformHorizontalScrollbar(scrollState: LazyListState) {
    HorizontalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState)
    )
}

@Composable
actual fun PlatformVerticalScrollbar(scrollState: LazyListState, modifier: Modifier) {
    VerticalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState),
        modifier = modifier,
    )
}

@Composable
actual fun PlatformVerticalScrollbar(scrollState: LazyGridState, modifier: Modifier) {
    VerticalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState),
        modifier = modifier,
    )
}