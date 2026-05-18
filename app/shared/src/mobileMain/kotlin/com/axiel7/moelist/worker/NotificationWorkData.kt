package com.axiel7.moelist.worker

import kotlinx.serialization.Serializable

@Serializable
data class NotificationWorkData(
    val animeId: Int,
    val animeTitle: String,
    val type: String? = null,
)
