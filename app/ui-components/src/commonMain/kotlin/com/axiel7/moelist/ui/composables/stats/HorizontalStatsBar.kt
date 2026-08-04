package com.axiel7.moelist.ui.composables.stats

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.data.model.base.LocalizableAndColorable
import com.axiel7.moelist.data.utils.NumExtensions.format
import com.axiel7.moelist.ui.base.model.Stat
import com.axiel7.moelist.ui.composables.PlatformHorizontalScrollbar
import com.axiel7.moelist.ui.composables.Rectangle
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.total_entries
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : LocalizableAndColorable> HorizontalStatsBar(
    stats: List<Stat<T>>,
    horizontalPadding: Dp = 8.dp,
) {
    val totalValue = remember(stats) {
        stats.map { it.value }.sum()
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val maxWidth = maxWidth
        Column {
            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = horizontalPadding)
            ) {
                items(stats) {
                    val percentage = remember(totalValue) {
                        (it.value * 100 / totalValue).format()
                    }
                    StatChip(
                        stat = it,
                        tooltipText = "$percentage%",
                        scope = scope,
                    )
                }
            }

            PlatformHorizontalScrollbar(scrollState = listState)

            Row(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                val minWidth = 4.dp
                val nonZeroStats = stats.count { it.value > 0 }
                val reservedWidth = minWidth * nonZeroStats
                val availableBarWidth = (maxWidth - (horizontalPadding * 2) - reservedWidth)
                    .coerceAtLeast(0.dp)

                stats.forEach {
                    if (it.value > 0 && totalValue > 0) {
                        val barWidth = minWidth + (it.value / totalValue * availableBarWidth.value).dp
                        Rectangle(
                            width = barWidth,
                            height = 20.dp,
                            color = it.type.primaryColor()
                        )
                    }
                }
            }

            Text(
                text = stringResource(
                    UiRes.string.total_entries,
                    totalValue.format() ?: totalValue.toString()
                ),
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = 4.dp
                ),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun HorizontalStatsBarPreview() {
    MoeListTheme {
        Surface {
            HorizontalStatsBar(
                stats = Stat.exampleStats
            )
        }
    }
}