package com.axiel7.moelist.screens.editmedia.composables

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axiel7.moelist.data.utils.NumExtensions.format
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
fun EditMediaProgressRow(
    label: String,
    icon: DrawableResource? = null,
    progress: Int?,
    modifier: Modifier = Modifier,
    totalProgress: Int?,
    onValueChange: (String) -> Unit,
    minValue: Int? = null,
    maxValue: Int? = null,
    onMinusClick: () -> Unit,
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
            BasicTextField(
                value = progress?.format().orEmpty(),
                onValueChange = onValueChange,
                modifier = Modifier.width(IntrinsicSize.Min),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textIndent = TextIndent(firstLine = 2.sp)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (progress == null) {
                            Text(
                                text = "0",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (totalProgress != null) {
                Text(
                    text = "/${totalProgress.format()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

            Text(
                text = label,
                modifier = Modifier.padding(
                    start = if (totalProgress == null) 8.dp else 4.dp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

        FilledTonalIconButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onMinusClick()
            },
            enabled = if (progress != null && minValue != null) progress > minValue else true,
            shapes = IconButtonDefaults.shapes()
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
            enabled = if (progress != null && maxValue != null) progress < maxValue else true,
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.ic_round_add_24),
                contentDescription = stringResource(UiRes.string.plus_one)
            )
        }
    }
}