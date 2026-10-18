package com.axiel7.moelist.screens.more.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.cancel
import com.axiel7.moelist.ui.generated.resources.logout
import com.axiel7.moelist.ui.generated.resources.logout_summary
import com.axiel7.moelist.ui.generated.resources.ok
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LogOutDialog(
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onAccept,
                shapes = ButtonDefaults.shapes(),
            ) {
                Text(text = stringResource(UiRes.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shapes = ButtonDefaults.shapes(),
            ) {
                Text(text = stringResource(UiRes.string.cancel))
            }
        },
        title = { Text(text = stringResource(UiRes.string.logout)) },
        text = {
            Text(text = stringResource(UiRes.string.logout_summary))
        }
    )
}