package com.axiel7.moelist.data.utils

private val characterEntities = mapOf(
    "&lt;" to "<",
    "&gt;" to ">",
    "&amp;" to "&",
    "&quot;" to "\"",
    "&apos;" to "'",
    "&nbsp;" to " ",
    "&copy;" to "©",
    "&reg;" to "®"
)

private val htmlEntityEncodingRegex = Regex("&[A-Za-z0-9]{2,};")

actual fun String.unescapeHtml(): String? {
    if (this.isEmpty()) return this

    val cleanText = StringBuilder()
    var lastIndex = 0
    
    val matches = htmlEntityEncodingRegex.findAll(this)
    for (match in matches) {
        if (match.range.first > lastIndex) {
            cleanText.append(this.substring(lastIndex, match.range.first))
        }
        
        val doomedText = match.value
        val newCharacter = characterEntities[doomedText]
        if (newCharacter != null) {
            cleanText.append(newCharacter)
            lastIndex = match.range.last + 1
        } else {
            lastIndex = match.range.first
        }
    }
    
    if (lastIndex < this.length) {
        cleanText.append(this.substring(lastIndex))
    }
    
    return cleanText.toString()
}