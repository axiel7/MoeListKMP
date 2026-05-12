plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.buildConfig) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.conveyor) apply false
    alias(libs.plugins.flatpakGradleGenerator)
}

apply(from = "gradle/releases.gradle.kts")

tasks.flatpakGradleGenerator {
    outputFile = project.file("app/desktopApp/packaging/flatpak/flatpak-sources-root.json")
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