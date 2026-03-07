package com.axiel7.moelist

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axiel7.moelist.main.MainViewModel
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.toBottomDestinationIndex
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.app_name
import com.axiel7.moelist.ui.generated.resources.moelist_round_v3
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import java.util.Locale

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalOpenIdConnect::class)
fun main() {
    val tokenStore = JvmKeyringTokenStore()
    initApp(tokenStore = tokenStore)

    System.setProperty("apple.awt.application.appearance", "system")

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = stringResource(UiRes.string.app_name),
            icon = painterResource(UiRes.drawable.moelist_round_v3),
            state = rememberWindowState(width = 1000.dp, height = 700.dp),
        ) {
            val viewModel = koinViewModel<MainViewModel>()
            val windowSizeClass = calculateWindowSizeClass()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            fun findLastTabOpened(): Int {
                val startTab = runBlocking { viewModel.startTab.first() }
                var lastTabOpened = startTab?.value?.toBottomDestinationIndex()

                if (lastTabOpened == null) {
                    lastTabOpened = runBlocking { viewModel.lastTab.first() }
                } else { // opened from intent or start tab setting
                    viewModel.saveLastTab(lastTabOpened)
                }
                return lastTabOpened
            }

            val lastTabOpened = remember { findLastTabOpened() }

            App(
                uiState = uiState,
                event = viewModel,
                lastTabOpened = lastTabOpened,
                windowWidthSizeClass = windowSizeClass.widthSizeClass,
                onLocaleChange = { Locale.setDefault(Locale.forLanguageTag(it.value)) }
            )
        }
    }
}