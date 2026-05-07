package com.axiel7.moelist.data.repository

import com.axiel7.moelist.data.model.AccessToken
import com.axiel7.moelist.data.model.Response
import com.axiel7.moelist.data.network.Api
import com.axiel7.moelist.data.network.OAuthService
import com.axiel7.moelist.data.utils.CLIENT_ID
import com.axiel7.moelist.data.utils.PkceGenerator
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect

class LoginRepository(
    private val api: Api,
    private val oAuthService: OAuthService,
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) {

    companion object {
        val codeVerifier = PkceGenerator.generateVerifier(length = 128)
        private const val GRANT_TYPE = "authorization_code"
    }

    @OptIn(ExperimentalOpenIdConnect::class)
    suspend fun getAccessToken(code: String): Response<AccessToken> {
        val accessToken = try {
            api.getAccessToken(
                clientId = CLIENT_ID,
                code = code,
                codeVerifier = codeVerifier,
                grantType = GRANT_TYPE
            )
        } catch (_: Exception) {
            null
        }

        return if (accessToken?.accessToken == null)
            Response(message = "Token was null: ${accessToken?.error}: ${accessToken?.message}")
        else {
            Response(data = accessToken)
        }
    }

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