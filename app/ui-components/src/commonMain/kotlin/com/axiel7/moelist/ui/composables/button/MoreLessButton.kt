package com.axiel7.moelist.ui.composables.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.expand_less_24
import com.axiel7.moelist.ui.generated.resources.expand_more_24
import com.axiel7.moelist.ui.generated.resources.show_less
import com.axiel7.moelist.ui.generated.resources.show_more
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MoreLessButton(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shapes = ButtonDefaults.shapes(),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        )
    ) {
        Icon(
            painter = painterResource(
                if (isExpanded) UiRes.drawable.expand_less_24 else UiRes.drawable.expand_more_24
            ),
            contentDescription = "expand_arrow",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 4.dp)
        )
        Text(
            text = stringResource(
                if (isExpanded) UiRes.string.show_less else UiRes.string.show_more
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}