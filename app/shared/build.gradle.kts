
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    android {
        namespace = "com.axiel7.moelist.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions.jvmTarget = JvmTarget.JVM_11
        androidResources.enable = true
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "com.axiel7.moelist.shared")
        }
    }
    
    jvm()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
            api(projects.core.resources)
            api(projects.app.uiComponents)

            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.windowSizeClass)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)

            api(libs.koin.compose.viewmodel.navigation)

            implementation(libs.kotlinx.datetime)

            api(libs.androidx.lifecycle.runtime.compose)
            api(libs.androidx.navigation3.ui)
            api(libs.androidx.lifecycle.viewmodel.navigation3)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            implementation(libs.androidx.datastore.preferences)

            implementation(libs.androidx.room.runtime)

            api(libs.oidc.appsupport)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        val nonAndroidMain by creating {
            dependsOn(commonMain.get())
        }
        configure(listOf(iosMain, jvmMain)) {
            get().dependsOn(nonAndroidMain)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.preference)
        }
        iosMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.java.keyring)
        }
    }

    jvmToolchain(21)
}

// Android-based preview support
dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}
