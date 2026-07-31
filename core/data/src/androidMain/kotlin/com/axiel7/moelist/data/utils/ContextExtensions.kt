package com.axiel7.moelist.data.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.LocaleListCompat

object ContextExtensions {
    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.showToast(@StringRes stringRes: Int) {
        showToast(getString(stringRes))
    }

    fun Context.copyToClipBoard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText("title", text))
        showToast("Copied")
    }

    fun getCurrentLanguageTag() = LocaleListCompat.getAdjustedDefault()[0]?.toLanguageTag()
}