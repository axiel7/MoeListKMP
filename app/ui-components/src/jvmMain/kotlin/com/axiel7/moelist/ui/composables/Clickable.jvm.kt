package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.onClick
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.secondaryClick(onClick: () -> Unit) = onClick(
    matcher = PointerMatcher.mouse(
        PointerButton.Secondary
    ),
    onClick = onClick,
)