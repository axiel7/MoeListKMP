package com.axiel7.moelist

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.axiel7.moelist.data.createDataStore
import com.axiel7.moelist.data.local.getDatabaseBuilder
import com.axiel7.moelist.data.model.ui.AppLanguage
import com.axiel7.moelist.main.MainViewModel
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.toBottomDestinationIndex
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.compose.viewmodel.koinViewModel
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.appsupport.IosCodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.tokenstore.IosKeychainTokenStore
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalOpenIdConnect::class)
@Suppress("unused") // Called from Swift
fun initApp() = initApp(
    tokenStore = IosKeychainTokenStore(),
    codeAuthFlowFactory = IosCodeAuthFlowFactory(),
    databaseBuilder = getDatabaseBuilder(),
    createDataStore = { createDataStore(it) }
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Suppress("unused") // Called from Swift
fun MainViewController() = ComposeUIViewController {
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
        windowWidthSizeClass = windowSizeClass.widthSizeClass,
        lastTabOpened = lastTabOpened,
        onLocaleChange = {
            if (it == AppLanguage.FOLLOW_SYSTEM) {
                NSUserDefaults.standardUserDefaults.removeObjectForKey("AppleLanguages")
            } else {
                NSUserDefaults.standardUserDefaults.setObject(
                    arrayListOf(it.value),
                    "AppleLanguages"
                )
            }
        }
    )
}