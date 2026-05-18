package com.axiel7.moelist

interface Platform {
    val name: String
    val type: PlatformType
    val supportsNotifications: Boolean
}

enum class PlatformType {
    ANDROID,
    IOS,
    JVM;
}

expect fun getPlatform(): Platform