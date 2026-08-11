package com.axiel7.moelist.ui.composables.chip

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.round_check_24
import com.axiel7.moelist.ui.generated.resources.round_close_24
import org.jetbrains.compose.resources.painterResource

@Composable
fun TriFilterChip(
    text: String,
    value: Boolean?,
    onValueChanged: (Boolean?) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilterChip(
        selected = value != null,
        onClick = {
            onValueChanged(
                when (value) {
                    null -> true
                    true -> false
                    false -> null
                }
            )
        },
        label = { Text(text = text) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = {
            if (value == true) {
                Icon(
                    painter = painterResource(UiRes.drawable.round_check_24),
                    contentDescription = "check",
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            } else if (value == false) {
                Icon(
                    painter = painterResource(UiRes.drawable.round_close_24),
                    contentDescription = "close",
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        },
        colors = if (value == false) {
            FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                labelColor = MaterialTheme.colorScheme.onErrorContainer,
                iconColor = MaterialTheme.colorScheme.onErrorContainer,
                selectedContainerColor = MaterialTheme.colorScheme.errorContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onErrorContainer,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        } else {
            FilterChipDefaults.filterChipColors()
        }
    )
}