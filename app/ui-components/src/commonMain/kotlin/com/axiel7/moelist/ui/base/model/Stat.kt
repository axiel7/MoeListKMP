package com.axiel7.moelist.ui.base.model

import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.LocalizableAndColorable

@Stable
data class Stat<T : LocalizableAndColorable>(
    val type: T,
    val value: Float,
) {
    companion object {
        val exampleStats = listOf(
            Stat(
                type = ListStatus.WATCHING,
                value = 114682f,
            ),
            Stat(
                type = ListStatus.COMPLETED,
                value = 39f,
            ),
            Stat(
                type = ListStatus.ON_HOLD,
                value = 3049f,
            ),
            Stat(
                type = ListStatus.DROPPED,
                value = 1084f,
            ),
            Stat(
                type = ListStatus.PLAN_TO_WATCH,
                value = 79278f,
            ),
        )
    }
}