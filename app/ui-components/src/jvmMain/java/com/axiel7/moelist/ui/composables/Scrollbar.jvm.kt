package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable

@Composable
actual fun PlatformHorizontalScrollbar(scrollState: LazyListState) {
    HorizontalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState)
    )
}