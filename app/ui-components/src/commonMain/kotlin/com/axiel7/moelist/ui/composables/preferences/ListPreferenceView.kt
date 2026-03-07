package com.axiel7.moelist.ui.composables.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ok
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> ListPreferenceView(
    title: String,
    values: List<T>,
    labelForValue: @Composable (T) -> String,
    value: T,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    onValueChange: (T) -> Unit
) {
    val windowInfo = LocalWindowInfo.current
    var openDialog by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { openDialog = true },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        } else {
            Spacer(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = value?.let { labelForValue(it) }.orEmpty(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = { Text(text = title) },
            text = {
                LazyColumn(
                    modifier = Modifier.sizeIn(
                        maxHeight = windowInfo.containerDpSize.height - 48.dp
                    )
                ) {
                    items(values) { entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onValueChange(entry) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = value == entry,
                                onClick = { onValueChange(entry) }
                            )
                            Text(text = labelForValue(entry))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        onValueChange(value)
                    },
                    shapes = ButtonDefaults.shapes(),
                ) {
                    Text(text = stringResource(UiRes.string.ok))
                }
            }
        )
    }
}
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> ListPreferenceView(
    title: String,
    entriesValues: Map<T, StringResource>,
    modifier: Modifier = Modifier,
    value: T,
    icon: DrawableResource? = null,
    onValueChange: (T) -> Unit
) {
    ListPreferenceView(
        title = title,
        values =  remember(entriesValues) { entriesValues.entries.map { it.key } },
        labelForValue = { value ->
            entriesValues[value]?.let { stringResource(it) }.orEmpty()
        },
        value = value,
        modifier = modifier,
        icon = icon,
        onValueChange = onValueChange,
    )
}