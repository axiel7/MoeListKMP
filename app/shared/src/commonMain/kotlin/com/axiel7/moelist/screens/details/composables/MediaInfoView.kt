package com.axiel7.moelist.screens.details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.unknown
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaInfoView(
    title: String,
    info: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .then(modifier)
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
        Column(modifier = Modifier.weight(1.4f)) {
            SelectionContainer {
                Text(
                    text = info ?: stringResource(UiRes.string.unknown),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaInfoPreview() {
    MoeListTheme {
        MediaInfoView(
            title = "Title",
            info = "Hello this is a large anime title do you like it?"
        )
    }
}