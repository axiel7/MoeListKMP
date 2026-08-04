package com.axiel7.moelist.screens.userlist.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_casino_24
import com.axiel7.moelist.ui.generated.resources.random
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RandomChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text = stringResource(UiRes.string.random)) },
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(UiRes.drawable.ic_round_casino_24),
                contentDescription = stringResource(UiRes.string.random),
                modifier = Modifier.size(20.dp),
            )
        }
    )
}