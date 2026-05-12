@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.flatpakGradleGenerator)
}

kotlin {
    applyDefaultHierarchyTemplate()

    android {
        namespace = "com.axiel7.moelist.resources"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions.jvmTarget = JvmTarget.JVM_11
        androidResources.enable = true
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            api(libs.compose.components.resources)
        }
    }

    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

compose.resources {
    publicResClass = true
    nameOfResClass = "UiRes"
    packageOfResClass = "com.axiel7.moelist.ui.generated.resources"
}

tasks.flatpakGradleGenerator {
    outputFile = project.file("../../app/desktopApp/packaging/flatpak/flatpak-sources-resources.json")
    downloadDirectory.set("./offline-repository")
    excludeConfigurations.set(
        listOf(
            "testCompileClasspath",
            "testRuntimeClasspath",
            "androidCompileClasspath",
            "androidMainLintChecksClasspath",
            "androidRuntimeClasspath"
        )
    )
}