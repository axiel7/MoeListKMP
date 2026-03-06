package com.axiel7.moelist.data.utils

import io.ktor.http.URLBuilder

object StringExtensions {
    /**
     * Returns a string representation of the object.
     * Can be called with a null receiver, in which case it returns `null`.
     */
    fun Any?.toStringOrNull() = this.toString().let { if (it == "null") null else it }

    /**
     * Returns a string representation of the object.
     * Can be called with a null receiver, in which case it returns an empty String.
     */
    fun Any?.toStringOrEmpty() = this.toString().let { if (it == "null") "" else it }

    fun String.urlEncode(): String = URLBuilder(this).buildString()
        .replace("http://localhost/", "")

    /**
     * Format the opening/ending text from MAL to use it on YouTube search
     */
    fun String.buildQueryFromThemeText() = this
        .replace("\"", "")
        .replaceFirst(Regex("#?\\w+:"), "") // theme number
        .replace(Regex("\\(ep.*\\)"), "") // episodes
        .trim()
        .urlEncode()
}

expect fun String.unescapeHtml(): String?