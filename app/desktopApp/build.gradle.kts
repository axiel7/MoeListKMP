import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    dependencies {
        implementation(projects.app.shared)
        implementation(compose.desktop.currentOs)
        implementation(libs.compose.components.resources)
        implementation(libs.kotlinx.coroutines.swing)
    }
}

compose.desktop {
    application {
        mainClass = "com.axiel7.moelist.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.AppImage)
            packageName = "MoeList"
            packageVersion = "1.0.0"
            description = "A Multiplatform MyAnimeList client"
            copyright = "© 2026 axiel7"

            windows {
                iconFile.set(project.file("icons/icon.ico"))
                menu = true // Add to Start Menu
            }
            macOS {
                iconFile.set(project.file("icons/icon.icns"))
                bundleID = "com.axiel7.moelist"
            }
            linux {
                iconFile.set(project.file("icons/icon.png"))
            }

            modules("jdk.unsupported")

            buildTypes.release.proguard {
                configurationFiles.from(project.file("proguard-rules.pro"))
                optimize.set(false)
            }
        }
    }
}

compose.resources {
    packageOfResClass = "com.axiel7.moelist.generated.resources.desktop"
}
