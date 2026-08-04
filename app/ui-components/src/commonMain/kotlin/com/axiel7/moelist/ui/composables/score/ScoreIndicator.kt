package com.axiel7.moelist.ui.composables.score

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axiel7.moelist.data.utils.NumExtensions.toStringPositiveValueOrUnknown
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_star_16
import com.axiel7.moelist.ui.generated.resources.mean_score
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SmallScoreIndicator(
    score: Float?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.ic_round_star_16),
            contentDescription = stringResource(UiRes.string.mean_score),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = score.toStringPositiveValueOrUnknown(),
            modifier = Modifier.padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmallScoreIndicatorPreview() {
    MoeListTheme {
        SmallScoreIndicator(score = 8.53f)
    }
}