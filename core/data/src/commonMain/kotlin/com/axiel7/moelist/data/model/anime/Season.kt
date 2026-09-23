package com.axiel7.moelist.data.model.anime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.axiel7.moelist.data.model.base.Localizable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.fall
import com.axiel7.moelist.ui.generated.resources.ic_fall_24
import com.axiel7.moelist.ui.generated.resources.ic_spring_24
import com.axiel7.moelist.ui.generated.resources.ic_summer_24
import com.axiel7.moelist.ui.generated.resources.ic_winter_24
import com.axiel7.moelist.ui.generated.resources.spring
import com.axiel7.moelist.ui.generated.resources.summer
import com.axiel7.moelist.ui.generated.resources.winter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Stable
@Serializable
enum class Season(
    val value: String,
    val icon: DrawableResource
) : Localizable {
    @SerialName("winter")
    WINTER(
        value = "winter",
        icon = UiRes.drawable.ic_winter_24
    ),

    @SerialName("spring")
    SPRING(
        value = "spring",
        icon = UiRes.drawable.ic_spring_24
    ),

    @SerialName("summer")
    SUMMER(
        value = "summer",
        icon = UiRes.drawable.ic_summer_24
    ),

    @SerialName("fall")
    FALL(
        value = "fall",
        icon = UiRes.drawable.ic_fall_24
    );

    @Composable
    override fun localized() = when (this) {
        WINTER -> stringResource(UiRes.string.winter)
        SPRING -> stringResource(UiRes.string.spring)
        SUMMER -> stringResource(UiRes.string.summer)
        FALL -> stringResource(UiRes.string.fall)
    }
}