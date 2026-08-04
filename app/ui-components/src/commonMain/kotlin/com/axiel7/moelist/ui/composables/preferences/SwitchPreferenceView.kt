package com.axiel7.moelist.ui.composables.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SwitchPreferenceView(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    value: Boolean,
    icon: DrawableResource? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    iconPadding: PaddingValues = PaddingValues(16.dp),
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onValueChange(!value)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    modifier = Modifier.padding(iconPadding),
                    tint = iconTint
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                )
            }

            Column(
                modifier = if (subtitle != null)
                    Modifier.padding(16.dp)
                else Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }//: Column
        }//: Row

        Switch(
            checked = value,
            onCheckedChange = {
                onValueChange(it)
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }//: Row
}