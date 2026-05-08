package com.axiel7.moelist.data.repository

import com.axiel7.moelist.data.network.OAuthService
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect

class LoginRepository(
    private val oAuthService: OAuthService,
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) {

    @OptIn(ExperimentalOpenIdConnect::class)
    suspend fun migrateLegacyData() {
        defaultPreferencesRepository.getLegacyTokens()?.let { accessToken ->
            oAuthService.tokenStore.saveTokens(
                accessToken = accessToken.accessToken ?: return,
                refreshToken = accessToken.refreshToken,
                idToken = null,
            )
            defaultPreferencesRepository.removeLegacyTokens()
        }
    }

    suspend fun logOut() {
        oAuthService.logOut()
        defaultPreferencesRepository.setProfilePicture(null)
    }
}