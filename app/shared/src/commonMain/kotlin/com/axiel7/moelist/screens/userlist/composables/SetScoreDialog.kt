package com.axiel7.moelist.screens.userlist.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.axiel7.moelist.data.model.media.scoreText
import com.axiel7.moelist.ui.composables.score.ScoreSlider
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.cancel
import com.axiel7.moelist.ui.generated.resources.ok
import com.axiel7.moelist.ui.generated.resources.score_value
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SetScoreDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var score by remember { mutableIntStateOf(0) }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(score) },
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
        title = {
            Text(text = stringResource(UiRes.string.score_value, score.scoreText()))
        },
        text = {
            ScoreSlider(
                score = score,
                onValueChange = { score = it }
            )
        }
    )
}