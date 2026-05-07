package com.axiel7.moelist

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val type: PlatformType = PlatformType.JVM
}

actual fun getPlatform(): Platform = JVMPlatform()