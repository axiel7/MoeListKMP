package com.axiel7.moelist.main

import androidx.compose.runtime.Stable

@Stable
interface MainEvent {
    fun saveLastTab(value: Int)
    fun getAccessToken(code: String)
}