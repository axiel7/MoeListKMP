package com.axiel7.moelist.screens.more.credits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import com.axiel7.moelist.data.utils.GENERAL_HELP_CREDIT_URL
import com.axiel7.moelist.data.utils.LOGO_CREDIT_URL
import com.axiel7.moelist.data.utils.LOGO_NEW_CREDIT_URL
import com.axiel7.moelist.data.utils.WEBSITE_CREDIT_URL
import com.axiel7.moelist.screens.more.composables.MoreItem
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.DefaultScaffoldWithTopAppBar
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.api_help
import com.axiel7.moelist.ui.generated.resources.contributors
import com.axiel7.moelist.ui.generated.resources.credits
import com.axiel7.moelist.ui.generated.resources.general_help
import com.axiel7.moelist.ui.generated.resources.logo_design
import com.axiel7.moelist.ui.generated.resources.new_logo_design
import com.axiel7.moelist.ui.generated.resources.support
import com.axiel7.moelist.ui.generated.resources.translations
import com.axiel7.moelist.ui.generated.resources.website
import com.axiel7.moelist.screens.more.settings.SettingsTitle
import com.axiel7.moelist.ui.theme.MoeListTheme
import org.jetbrains.compose.resources.stringResource

val contributorsCredits = mapOf(
    "@uragiristereo" to "https://github.com/uragiristereo",
    "@krishnapandey24" to "https://github.com/krishnapandey24",
)

val translationsCredits = mapOf(
    "ar-SA" to "@sakugaky, @WhiteCanvas, @Comikazie, @mlvin, @bobteen1",
    "bg-BG" to "@itzlighter",
    "cs" to "@J4kub07, @gxs3lium",
    "de" to "@Secresa, @MaximilianGT500, @Fuchs23",
    "es" to "@axiel7",
    "fr" to "@mamanamgae, @frosqh, @Eria78, @nesquick",
    "in-ID" to "@Clxf12",
    "it-IT" to "@AntoF98, @DomeF, @pasthor",
    "ja" to "@axiel7, @Ulong32, @watashibeme",
    "pl" to "@YOGI_AOGI",
    "pt-BR" to "@RickyM7, @SamOak",
    "pt-PT" to "@SamOak, @DemiCool",
    "ru-RU" to "@grin3671, @Ronner231",
    "sk" to "@gxs3lium",
    "tr" to "@hsinankirdar, @kyoya",
    "uk-UA" to "@Sensetivity",
    "zh-Hans" to "@bengerlorf",
    "zh-Hant" to "@jhih_yu_lin, @web790709",
)

@Composable
fun CreditsView(
    navActionManager: NavActionManager
) {
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    DefaultScaffoldWithTopAppBar(
        title = stringResource(UiRes.string.credits),
        navigateBack = navActionManager::goBack
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(it)
        ) {
            HorizontalDivider()
            SettingsTitle(text = stringResource(UiRes.string.support))
            MoreItem(
                title = stringResource(UiRes.string.logo_design),
                subtitle = "@danielvd_art",
                onClick = { uriHandler.openUri(LOGO_CREDIT_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.new_logo_design),
                subtitle = "@WSTxda",
                onClick = { uriHandler.openUri(LOGO_NEW_CREDIT_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.website),
                subtitle = "@MaximilianGT500",
                onClick = { uriHandler.openUri(WEBSITE_CREDIT_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.general_help),
                subtitle = "@Jeluchu",
                onClick = { uriHandler.openUri(GENERAL_HELP_CREDIT_URL) }
            )
            MoreItem(
                title = stringResource(UiRes.string.api_help),
                subtitle = "@Glodanif",
                onClick = {}
            )
            HorizontalDivider()
            SettingsTitle(text = stringResource(UiRes.string.contributors))
            contributorsCredits.forEach { (username, link) ->
                MoreItem(
                    title = username,
                    onClick = { uriHandler.openUri(link) }
                )
            }
            HorizontalDivider()
            SettingsTitle(text = stringResource(UiRes.string.translations))
            translationsCredits.forEach { (lang, credit) ->
                val displayName = remember { Locale(lang).language }
                MoreItem(
                    title = displayName,
                    subtitle = credit,
                    onClick = { }
                )
            }
        }
    }
}

@Preview
@Composable
fun CreditsPreview() {
    MoeListTheme {
        Surface {
            CreditsView(
                navActionManager = NavActionManager.rememberNavActionManager()
            )
        }
    }
}