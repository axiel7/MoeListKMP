package com.axiel7.moelist

interface Platform {
    val name: String
    val type: PlatformType
}

enum class PlatformType {
    ANDROID,
    IOS,
    JVM;
}

expect fun getPlatform(): Platform