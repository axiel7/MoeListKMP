import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.conveyor)
}

val versionProps = Properties().also {
    it.load(project.rootProject.file("version.properties").reader())
}

version = versionProps.getProperty("name")

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.JETBRAINS)
    }
    dependencies {
        implementation(projects.app.shared)
        implementation(compose.desktop.currentOs)
        implementation(libs.compose.components.resources)
        implementation(libs.kotlinx.coroutines.swing)
        implementation(libs.conveyor.control)
    }
}

dependencies {
    // Use the configurations created by the Conveyor plugin to tell Gradle/Conveyor where to find the artifacts for each platform.
    linuxAmd64(compose.desktop.linux_x64)
    macAmd64(compose.desktop.macos_x64)
    macAarch64(compose.desktop.macos_arm64)
    windowsAmd64(compose.desktop.windows_x64)
}

compose.desktop {
    application {
        mainClass = "com.axiel7.moelist.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.AppImage)
            packageName = "moelist"
            packageVersion = versionProps.getProperty("name")
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
