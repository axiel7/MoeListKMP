package com.axiel7.moelist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.axiel7.moelist.data.model.ui.AppLanguage
import com.axiel7.moelist.data.model.ui.ThemeStyle
import com.axiel7.moelist.main.MainViewModel
import com.axiel7.moelist.ui.base.model.BottomDestination.Companion.toBottomDestinationIndex
import com.axiel7.moelist.ui.base.navigation.DeepLink
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory

class MainActivity : AppCompatActivity() {

    val viewModel by viewModel<MainViewModel>()

    val codeAuthFlowFactory by inject<CodeAuthFlowFactory>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen()
        (codeAuthFlowFactory as? AndroidCodeAuthFlowFactory)?.registerActivity(this)

        lifecycleScope.launch {
            lifecycle.withCreated {
                viewModel.continueAuthFlow()
            }
        }

        viewModel.setDeepLink(findDeepLink(intent))

        runBlocking { viewModel.initGlobalVariables() }

        val lastTabOpened = findLastTabOpened()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val isDarkTheme = if (uiState.theme == ThemeStyle.FOLLOW_SYSTEM) isSystemInDarkTheme()
            else uiState.theme == ThemeStyle.DARK
            val windowSizeClass = calculateWindowSizeClass(this)

            App(
                dynamicColorSeed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (isDarkTheme) dynamicDarkColorScheme(this).primary
                    else dynamicLightColorScheme(this).primary
                } else null,
                uiState = uiState,
                event = viewModel,
                windowWidthSizeClass = windowSizeClass.widthSizeClass,
                lastTabOpened = lastTabOpened,
                onLocaleChange = ::changeLocale,
            )

            DisposableEffect(isDarkTheme) {
                val systemBarStyle = SystemBarStyle.auto(
                    lightScrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT,
                    detectDarkMode = { isDarkTheme }
                )
                enableEdgeToEdge(
                    statusBarStyle = systemBarStyle,
                    navigationBarStyle = systemBarStyle,
                )
                onDispose {}
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel.setDeepLink(findDeepLink(intent))
    }

    private fun findDeepLink(intent: Intent): DeepLink<*>? {
        return findMediaDeepLink(intent)
    }

    private fun findMediaDeepLink(intent: Intent): DeepLink<*>? {
        fun deepLink(mediaId: Int, mediaType: String) = DeepLink(
            type = if (mediaType == "ANIME") DeepLink.Type.ANIME else DeepLink.Type.MANGA,
            data = mediaId
        )

        return if (intent.action == "details") {
            val mediaId = intent.getIntExtra("media_id", 0)
            val mediaType = intent.getStringExtra("media_type")?.uppercase()
            if (mediaId != 0 && mediaType != null) deepLink(mediaId, mediaType) else null
        } else if (intent.data?.host == "myanimelist.net") {
            // Only handle main details links
            val isMainDetails = intent.data?.pathSegments?.any {
                when (it) {
                    "character", "episode", "video", "reviews", "stacks", "news", "forum",
                    "clubs", "moreinfo" -> true

                    else -> false
                }
            } == false
            if (!isMainDetails) {
                return intent.dataString?.let {
                    DeepLink(type = DeepLink.Type.EXTERNAL, data = it)
                }
            }

            val mediaType = intent.data?.pathSegments?.getOrNull(0)?.uppercase()
            val mediaId = intent.data?.pathSegments?.getOrNull(1)?.toIntOrNull()
            if (mediaType != null && mediaId != null) deepLink(mediaId, mediaType) else null
        } else null
    }

    private fun findLastTabOpened(): Int {
        val startTab = runBlocking { viewModel.startTab.first() }
        var lastTabOpened =
            intent.action?.toBottomDestinationIndex() ?: startTab?.value?.toBottomDestinationIndex()

        if (lastTabOpened == null) {
            lastTabOpened = runBlocking { viewModel.lastTab.first() }
        } else { // opened from intent or start tab setting
            viewModel.saveLastTab(lastTabOpened)
        }
        return lastTabOpened
    }

    private fun changeLocale(language: AppLanguage) {
        val appLocale = if (language == AppLanguage.FOLLOW_SYSTEM) LocaleListCompat.getEmptyLocaleList()
        else LocaleListCompat.forLanguageTags(language.value)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}