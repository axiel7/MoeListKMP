package com.axiel7.moelist.screens.more.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.cancel
import com.axiel7.moelist.ui.generated.resources.delete
import com.axiel7.moelist.ui.generated.resources.delete_all
import com.axiel7.moelist.ui.generated.resources.delete_outline_24
import com.axiel7.moelist.ui.generated.resources.notifications
import com.axiel7.moelist.ui.generated.resources.ok
import com.axiel7.moelist.ui.generated.resources.round_delete_sweep_24
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun NotificationsView(
    navActionManager: NavActionManager
) {
    val viewModel: NotificationsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NotificationsViewContent(
        uiState = uiState,
        event = viewModel,
        navActionManager = navActionManager,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun NotificationsViewContent(
    uiState: NotificationsUiState,
    event: NotificationsEvent?,
    navActionManager: NavActionManager,
) {
    var isShowingDeleteAllDialog by remember { mutableStateOf(false) }

    if (isShowingDeleteAllDialog) {
        AlertDialog(
            onDismissRequest = { isShowingDeleteAllDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        event?.removeAllNotifications()
                        isShowingDeleteAllDialog = false
                    }
                ) {
                    Text(text = stringResource(UiRes.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { isShowingDeleteAllDialog = false }) {
                    Text(text = stringResource(UiRes.string.cancel))
                }
            },
            title = { Text(text = stringResource(UiRes.string.delete_all)) }
        )
    }

    DefaultScaffoldWithTopAppBar(
        title = stringResource(UiRes.string.notifications),
        navigateBack = navActionManager::goBack,
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { isShowingDeleteAllDialog = true }) {
                Icon(
                    painter = painterResource(UiRes.drawable.round_delete_sweep_24),
                    contentDescription = stringResource(UiRes.string.delete_all)
                )
                Text(
                    text = stringResource(UiRes.string.delete_all),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp) + padding
        ) {
            items(
                items = uiState.notifications?.asMap()?.keys?.toTypedArray().orEmpty()
            ) { key ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = dropUnlessResumed {
                            key.toString().toIntOrNull()?.let { id ->
                                navActionManager.toMediaDetails(
                                    mediaType = MediaType.ANIME,
                                    id = id
                                )
                            }
                        }),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (uiState.notifications?.get(key) as? String).orEmpty(),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f)
                    )
                    IconButton(
                        onClick = {
                            event?.removeNotification(key.name.toIntOrNull() ?: 0)
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(
                            painter = painterResource(UiRes.drawable.delete_outline_24),
                            contentDescription = stringResource(UiRes.string.delete)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NotificationsPreview() {
    MoeListTheme {
        Surface {
            NotificationsViewContent(
                uiState = NotificationsUiState(),
                event = null,
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}