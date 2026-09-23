package com.axiel7.moelist.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axiel7.moelist.data.model.ui.TabRowItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> TabRowWithPager(
    tabs: ImmutableList<TabRowItem<T>>,
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    beyondBoundsPageCount: Int = 0,
    isTabScrollable: Boolean = false,
    isPrimaryTab: Boolean = true,
    pageContent: @Composable (Int) -> Unit,
) {
    val state = rememberPagerState(initialPage = initialPage) { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        val tabsLayout = @Composable {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = state.currentPage == index,
                    onClick = { scope.launch { state.animateScrollToPage(index) } },
                    text = item.title?.let { title ->
                        {
                            Text(text = stringResource(title))
                        }
                    },
                    icon = item.icon?.let { icon ->
                        {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = item.value.toString()
                            )
                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (isTabScrollable) {
            if (isPrimaryTab) {
                PrimaryScrollableTabRow(
                    selectedTabIndex = state.currentPage,
                    edgePadding = 16.dp,
                    divider = {},
                    tabs = tabsLayout
                )
            } else {
                SecondaryScrollableTabRow(
                    selectedTabIndex = state.currentPage,
                    edgePadding = 16.dp,
                    divider = {},
                    tabs = tabsLayout
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        } else {
            if (isPrimaryTab) {
                PrimaryTabRow(
                    selectedTabIndex = state.currentPage,
                    tabs = tabsLayout
                )
            } else {
                SecondaryTabRow(
                    selectedTabIndex = state.currentPage,
                    tabs = tabsLayout
                )
            }
        }

        HorizontalPager(
            state = state,
            beyondViewportPageCount = if (beyondBoundsPageCount < 0) 0 else beyondBoundsPageCount,
        ) { page ->
            if (
                page !in ((state.currentPage - (beyondBoundsPageCount + 1))
                        ..(state.currentPage + (beyondBoundsPageCount + 1)))
            ) {
                // To make sure only X offscreen pages are being composed
                return@HorizontalPager
            }
            pageContent(page)
        }
    }//: Column
}