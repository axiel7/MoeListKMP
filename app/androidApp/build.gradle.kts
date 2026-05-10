import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
   alias(libs.plugins.androidApplication)
   alias(libs.plugins.composeMultiplatform)
   alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    implementation(projects.app.shared)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation3.ui)
    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
}

val versionProps = Properties().also {
    it.load(project.rootProject.file("version.properties").reader())
}

android {
    namespace = "com.axiel7.moelist"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.axiel7.moelist"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionProps.getProperty("code").toInt()
        versionName = versionProps.getProperty("name")
        addManifestPlaceholders(
            mapOf("oidcRedirectScheme" to "moelist")
        )
    }

    androidResources {
        localeFilters += listOf(
            "en", "ar-rSA", "bg-rBG", "cs-rCZ", "de", "es", "fr", "in-rID", "it-rIT", "ja",
            "pl-rPL", "pt-rBR", "pt-rPT", "ru-rRU", "sk-rSK", "tr", "uk-rUA", "zh", "zh-rTW"
        )
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dependenciesInfo {
        includeInApk = false
    }
}

base {
    archivesName = "moelist-${versionProps.getProperty("name")}"
}