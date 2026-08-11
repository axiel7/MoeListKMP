package com.axiel7.moelist.screens.userlist.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.data.model.media.MediaSort
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.ui.composables.chip.ChipWithMenu
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_sort_24
import com.axiel7.moelist.ui.generated.resources.sort_by
import com.axiel7.moelist.screens.userlist.UserMediaListEvent
import com.axiel7.moelist.screens.userlist.UserMediaListUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SortChip(
    uiState: UserMediaListUiState,
    event: UserMediaListEvent?,
    modifier: Modifier = Modifier
) {
    ChipWithMenu(
        title = uiState.listSort?.localized() ?: stringResource(UiRes.string.sort_by),
        values = if (uiState.mediaType == MediaType.MANGA) MediaSort.mangaListSortItems
        else MediaSort.animeListSortItems,
        selectedValue = uiState.listSort,
        onValueSelected = { value ->
            if (value != null) event?.onChangeSort(value)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(UiRes.drawable.ic_round_sort_24),
                contentDescription = stringResource(UiRes.string.sort_by),
                modifier = Modifier.size(20.dp),
            )
        },
        valueString = { it.localized() }
    )
}