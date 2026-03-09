package com.axiel7.moelist.screens.more.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.model.media.TitleLanguage
import com.axiel7.moelist.data.model.ui.AppLanguage
import com.axiel7.moelist.data.model.ui.ItemsPerRow
import com.axiel7.moelist.data.model.ui.ListStyle
import com.axiel7.moelist.data.model.ui.StartTab
import com.axiel7.moelist.data.model.ui.TabletMode
import com.axiel7.moelist.data.model.ui.ThemeStyle
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.composables.button.OpenByDefaultSettingsButton
import com.axiel7.moelist.ui.composables.preferences.ListPreferenceView
import com.axiel7.moelist.ui.composables.preferences.PlainPreferenceView
import com.axiel7.moelist.ui.composables.preferences.SwitchPreferenceView
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.always_load_characters
import com.axiel7.moelist.ui.generated.resources.black_theme_variant
import com.axiel7.moelist.ui.generated.resources.color_palette
import com.axiel7.moelist.ui.generated.resources.content
import com.axiel7.moelist.ui.generated.resources.default_section
import com.axiel7.moelist.ui.generated.resources.display
import com.axiel7.moelist.ui.generated.resources.enable_list_tabs
import com.axiel7.moelist.ui.generated.resources.enable_list_tabs_subtitle
import com.axiel7.moelist.ui.generated.resources.experimental
import com.axiel7.moelist.ui.generated.resources.format_paint_24
import com.axiel7.moelist.ui.generated.resources.hide_scores
import com.axiel7.moelist.ui.generated.resources.ic_round_color_lens_24
import com.axiel7.moelist.ui.generated.resources.ic_round_details_star_24
import com.axiel7.moelist.ui.generated.resources.ic_round_home_24
import com.axiel7.moelist.ui.generated.resources.ic_round_language_24
import com.axiel7.moelist.ui.generated.resources.items_per_row
import com.axiel7.moelist.ui.generated.resources.language
import com.axiel7.moelist.ui.generated.resources.list_style
import com.axiel7.moelist.ui.generated.resources.no_adult_content_24
import com.axiel7.moelist.ui.generated.resources.nsfw_summary
import com.axiel7.moelist.ui.generated.resources.pinned_navigation_bar
import com.axiel7.moelist.ui.generated.resources.random_button_on_list
import com.axiel7.moelist.ui.generated.resources.round_format_list_bulleted_24
import com.axiel7.moelist.ui.generated.resources.round_grid_view_24
import com.axiel7.moelist.ui.generated.resources.round_title_24
import com.axiel7.moelist.ui.generated.resources.settings
import com.axiel7.moelist.ui.generated.resources.show_nsfw
import com.axiel7.moelist.ui.generated.resources.tablet_android_24
import com.axiel7.moelist.ui.generated.resources.tablet_mode
import com.axiel7.moelist.ui.generated.resources.theme
import com.axiel7.moelist.ui.generated.resources.title_language
import com.axiel7.moelist.ui.generated.resources.use_separated_list_styles
import com.axiel7.moelist.ui.theme.MoeListTheme
import com.materialkolor.PaletteStyle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsView(
    navActionManager: NavActionManager
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsViewContent(
        uiState = uiState,
        event = viewModel,
        navActionManager = navActionManager,
    )
}

@Composable
private fun SettingsViewContent(
    uiState: SettingsUiState,
    event: SettingsEvent?,
    navActionManager: NavActionManager
) {
    //val context = LocalContext.current

    DefaultScaffoldWithTopAppBar(
        title = stringResource(UiRes.string.settings),
        navigateBack = navActionManager::goBack
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            SettingsTitle(text = stringResource(UiRes.string.display))

            ListPreferenceView(
                title = stringResource(UiRes.string.theme),
                entriesValues = ThemeStyle.entriesLocalized,
                value = uiState.theme,
                icon = UiRes.drawable.ic_round_color_lens_24,
                onValueChange = { event?.setTheme(it) }
            )

            ListPreferenceView(
                title = stringResource(UiRes.string.color_palette),
                values = PaletteStyle.entries,
                labelForValue = { it.name },
                value = uiState.paletteStyle,
                icon = UiRes.drawable.format_paint_24,
                onValueChange = { event?.setPaletteStyle(it) }
            )

            SwitchPreferenceView(
                title = stringResource(UiRes.string.black_theme_variant),
                value = uiState.useBlackColors,
                onValueChange = { event?.setUseBlackColors(it) }
            )

            ListPreferenceView(
                title = stringResource(UiRes.string.language),
                entriesValues = AppLanguage.entriesLocalized,
                value = uiState.language,
                icon = UiRes.drawable.ic_round_language_24,
                onValueChange = { event?.setLanguage(it) }
            )

            ListPreferenceView(
                title = stringResource(UiRes.string.title_language),
                entriesValues = TitleLanguage.entriesLocalized,
                value = uiState.titleLanguage,
                icon = UiRes.drawable.round_title_24,
                onValueChange = { event?.setTitleLanguage(it) }
            )

            ListPreferenceView(
                title = stringResource(UiRes.string.default_section),
                entriesValues = StartTab.entriesLocalized,
                value = uiState.startTab,
                icon = UiRes.drawable.ic_round_home_24,
                onValueChange = { event?.setStartTab(it) }
            )

            SwitchPreferenceView(
                title = stringResource(UiRes.string.pinned_navigation_bar),
                value = uiState.pinnedNavBar,
                onValueChange = { event?.setPinnedNavBar(it) }
            )

            ListPreferenceView(
                title = stringResource(UiRes.string.tablet_mode),
                entriesValues = TabletMode.entriesLocalized,
                value = uiState.tabletMode,
                icon = UiRes.drawable.tablet_android_24,
                onValueChange = { event?.setTabletMode(it) }
            )

            SwitchPreferenceView(
                title = stringResource(UiRes.string.use_separated_list_styles),
                value = !uiState.useGeneralListStyle,
                onValueChange = { event?.setUseGeneralListStyle(!it) }
            )

            if (uiState.useGeneralListStyle) {
                ListPreferenceView(
                    title = stringResource(UiRes.string.list_style),
                    entriesValues = ListStyle.entriesLocalized,
                    value = uiState.generalListStyle,
                    icon = UiRes.drawable.round_format_list_bulleted_24,
                    onValueChange = { event?.setGeneralListStyle(it) }
                )
            } else {
                PlainPreferenceView(
                    title = stringResource(UiRes.string.list_style),
                    icon = UiRes.drawable.round_format_list_bulleted_24,
                    onClick = dropUnlessResumed { navActionManager.toListStyleSettings() }
                )
            }

            if (uiState.generalListStyle == ListStyle.GRID || !uiState.useGeneralListStyle) {
                ListPreferenceView(
                    title = stringResource(UiRes.string.items_per_row),
                    entriesValues = ItemsPerRow.entriesLocalized,
                    value = uiState.itemsPerRow,
                    icon = UiRes.drawable.round_grid_view_24,
                    onValueChange = { event?.setItemsPerRow(it) }
                )
            }

            SettingsTitle(text = stringResource(UiRes.string.content))

            SwitchPreferenceView(
                title = stringResource(UiRes.string.show_nsfw),
                subtitle = stringResource(UiRes.string.nsfw_summary),
                value = uiState.showNsfw,
                icon = UiRes.drawable.no_adult_content_24,
                onValueChange = { event?.setShowNsfw(it) }
            )

            SwitchPreferenceView(
                title = stringResource(UiRes.string.hide_scores),
                value = uiState.hideScores,
                icon = UiRes.drawable.ic_round_details_star_24,
                onValueChange = { event?.setHideScores(it) }
            )

            OpenByDefaultSettingsButton()

            SettingsTitle(text = stringResource(UiRes.string.experimental))

            SwitchPreferenceView(
                title = stringResource(UiRes.string.enable_list_tabs),
                subtitle = stringResource(UiRes.string.enable_list_tabs_subtitle),
                value = uiState.useListTabs,
                onValueChange = {
                    event?.setUseListTabs(it)
                }
            )

            SwitchPreferenceView(
                title = stringResource(UiRes.string.always_load_characters),
                value = uiState.loadCharacters,
                onValueChange = { event?.setLoadCharacters(it) }
            )
            SwitchPreferenceView(
                title = stringResource(UiRes.string.random_button_on_list),
                value = uiState.randomListEntryEnabled,
                onValueChange = { event?.setRandomListEntryEnabled(it) }
            )
        }
    }
}

@Composable
fun SettingsTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(start = 72.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview
@Composable
fun SettingsPreview() {
    MoeListTheme {
        Surface {
            SettingsViewContent(
                uiState = SettingsUiState(),
                event = null,
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}