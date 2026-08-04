package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.round_check_24
import com.axiel7.moelist.ui.generated.resources.sort_by
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> ChipWithMenu(
    title: String,
    values: List<T>,
    selectedValue: T?,
    onValueSelected: (T?) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    valueString: @Composable (T) -> String = { it.toString() },
    valueIcon: (T) -> DrawableResource? = { null },
) {
    val windowHeight = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp()
    }
    var menuOpened by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        AssistChip(
            onClick = { menuOpened = true },
            label = {
                Text(text = selectedValue?.let { valueString(it) } ?: title)
            },
            leadingIcon = leadingIcon,
        )
        DropdownMenu(
            expanded = menuOpened,
            onDismissRequest = { menuOpened = false },
            modifier = Modifier.requiredSizeIn(maxHeight = windowHeight / 2)
        ) {
            values.fastForEach {  item ->
                val checked = selectedValue == item
                DropdownMenuItem(
                    onClick = {
                        onValueSelected(item.takeIf { it != selectedValue })
                        menuOpened = false
                    },
                    text = { Text(text = valueString(item)) },
                    leadingIcon = {
                        if (checked) {
                            Icon(
                                painter = painterResource(UiRes.drawable.round_check_24),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                        } else {
                            valueIcon(item)?.let { iconRes ->
                                Icon(
                                    painter = painterResource(iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipWithMenuPreview() {
    MoeListTheme {
        ChipWithMenu(
            title = stringResource(UiRes.string.sort_by),
            values = listOf("Title", "Score"),
            selectedValue = null,
            onValueSelected = {},
        )
    }
}