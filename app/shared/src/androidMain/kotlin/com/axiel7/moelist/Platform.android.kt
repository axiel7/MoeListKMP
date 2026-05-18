package com.axiel7.moelist

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val type: PlatformType = PlatformType.ANDROID
    override val supportsNotifications: Boolean = true
}

actual fun getPlatform(): Platform = AndroidPlatform()