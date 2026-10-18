package com.axiel7.moelist.screens.editmedia.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_add_24
import com.axiel7.moelist.ui.generated.resources.minus_one
import com.axiel7.moelist.ui.generated.resources.plus_one
import com.axiel7.moelist.ui.generated.resources.round_remove_24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EditMediaValueRow(
    label: String,
    icon: DrawableResource? = null,
    modifier: Modifier = Modifier,
    minusEnabled: Boolean = true,
    onMinusClick: () -> Unit,
    plusEnabled: Boolean = true,
    onPlusClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = label,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
            )
        }

        FilledTonalIconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onMinusClick()
            },
            enabled = minusEnabled,
            shapes = IconButtonDefaults.shapes(),
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.round_remove_24),
                contentDescription = stringResource(UiRes.string.minus_one)
            )
        }
        FilledTonalIconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onPlusClick()
            },
            enabled = plusEnabled,
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.ic_round_add_24),
                contentDescription = stringResource(UiRes.string.plus_one)
            )
        }
    }
}