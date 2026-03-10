@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.buildConfig)
}

val privateProps = Properties().also {
    it.load(project.rootProject.file("private.properties").reader())
}

kotlin {
    applyDefaultHierarchyTemplate()

    android {
        namespace = "com.axiel7.moelist.data"
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
            implementation(projects.core.resources)

            api(libs.ktor.client.core)
            api(libs.ktor.client.logging)
            api(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            api(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.compose.runtime)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui)
            implementation(libs.material.kolor)

            implementation(libs.androidx.datastore.preferences)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            api(libs.oidc.appsupport)
            api(libs.oidc.ktor)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.commons.text)
        }
        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
            implementation(libs.commons.text)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.kotlinx.coroutines.core)
        }
    }

    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspJvm", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

buildConfig {
    buildConfigField("CLIENT_SECRET", expect<String>())

    sourceSets.named("androidMain") {
        buildConfigField("CLIENT_SECRET", "")
    }
    sourceSets.named("iosMain") {
        buildConfigField("CLIENT_SECRET", "")
    }
    sourceSets.named("jvmMain") {
        buildConfigField("CLIENT_SECRET", privateProps.getProperty("CLIENT_SECRET"))
    }
}