package com.axiel7.moelist.data.model.media

import androidx.compose.runtime.Composable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.data.model.ui.TabRowItem
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.friday
import com.axiel7.moelist.ui.generated.resources.monday
import com.axiel7.moelist.ui.generated.resources.saturday
import com.axiel7.moelist.ui.generated.resources.sunday
import com.axiel7.moelist.ui.generated.resources.thursday
import com.axiel7.moelist.ui.generated.resources.tuesday
import com.axiel7.moelist.ui.generated.resources.wednesday
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
enum class WeekDay : Localizable {
    @SerialName("monday")
    MONDAY,

    @SerialName("tuesday")
    TUESDAY,

    @SerialName("wednesday")
    WEDNESDAY,

    @SerialName("thursday")
    THURSDAY,

    @SerialName("friday")
    FRIDAY,

    @SerialName("saturday")
    SATURDAY,

    @SerialName("sunday")
    SUNDAY;

    @Composable
    override fun localized() = stringResource(stringRes)

    val stringRes
        get() = when (this) {
            MONDAY -> UiRes.string.monday
            TUESDAY -> UiRes.string.tuesday
            WEDNESDAY -> UiRes.string.wednesday
            THURSDAY -> UiRes.string.thursday
            FRIDAY -> UiRes.string.friday
            SATURDAY -> UiRes.string.saturday
            SUNDAY -> UiRes.string.sunday
        }

    companion object {
        val tabRowItems =
            entries.map { TabRowItem(value = it, title = it.stringRes) }.toImmutableList()
    }
}