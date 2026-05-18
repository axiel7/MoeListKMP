package com.axiel7.moelist

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val type: PlatformType = PlatformType.JVM
    override val supportsNotifications: Boolean = false
}

actual fun getPlatform(): Platform = JVMPlatform()