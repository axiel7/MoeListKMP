package com.axiel7.moelist.main.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.axiel7.moelist.ui.base.navigation.Navigator
import com.axiel7.moelist.ui.base.model.BottomDestination
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.Icon
import com.axiel7.moelist.ui.base.navigation.Route
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_search_24
import com.axiel7.moelist.ui.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainNavigationRail(
    navigator: Navigator,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        header = {
            FloatingActionButton(
                onClick = {
                    onItemSelected(-1)
                    navigator.navigate(Route.Search())
                }
            ) {
                Icon(
                    painter = painterResource(UiRes.drawable.ic_round_search_24),
                    contentDescription = stringResource(UiRes.string.search)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomDestination.railValues.forEachIndexed { index, dest ->
                val isSelected = dest.route == navigator.state.topLevelRoute
                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        onItemSelected(index)
                        navigator.navigate(dest.route)
                    },
                    icon = { dest.Icon(selected = isSelected) },
                    label = { Text(text = stringResource(dest.title)) }
                )
            }
        }
    }
}
