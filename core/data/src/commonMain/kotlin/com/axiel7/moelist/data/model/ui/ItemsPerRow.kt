package com.axiel7.moelist.data.model.ui

import androidx.compose.runtime.Stable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.default_setting
import com.axiel7.moelist.ui.generated.resources.eight
import com.axiel7.moelist.ui.generated.resources.five
import com.axiel7.moelist.ui.generated.resources.four
import com.axiel7.moelist.ui.generated.resources.nine
import com.axiel7.moelist.ui.generated.resources.one
import com.axiel7.moelist.ui.generated.resources.seven
import com.axiel7.moelist.ui.generated.resources.six
import com.axiel7.moelist.ui.generated.resources.ten
import com.axiel7.moelist.ui.generated.resources.three
import com.axiel7.moelist.ui.generated.resources.two
import kotlinx.collections.immutable.toImmutableMap

@Stable
enum class ItemsPerRow(val value: Int) {
    DEFAULT(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    val stringRes
        get() = when (this) {
            DEFAULT -> UiRes.string.default_setting
            ONE -> UiRes.string.one
            TWO -> UiRes.string.two
            THREE -> UiRes.string.three
            FOUR -> UiRes.string.four
            FIVE -> UiRes.string.five
            SIX -> UiRes.string.six
            SEVEN -> UiRes.string.seven
            EIGHT -> UiRes.string.eight
            NINE -> UiRes.string.nine
            TEN -> UiRes.string.ten
        }

    companion object {
        fun valueOf(value: Int) = entries.find { it.value == value }

        val entriesLocalized = entries.associateWith { it.stringRes }.toImmutableMap()
    }
}