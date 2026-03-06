package com.axiel7.moelist.data.repository

import com.axiel7.moelist.data.GlobalVariables
import com.axiel7.moelist.data.model.AccessToken
import com.axiel7.moelist.data.model.Response
import com.axiel7.moelist.data.network.Api
import com.axiel7.moelist.data.network.KtorClient
import com.axiel7.moelist.data.utils.PkceGenerator

class LoginRepository(
    private val globalVariables: GlobalVariables,
    private val api: Api,
    private val defaultPreferencesRepository: DefaultPreferencesRepository
) {

    companion object {
        const val STATE = "MoeList123"
        private const val GRANT_TYPE = "authorization_code"
        private val codeVerifier = PkceGenerator.generateVerifier(length = 128)
        val loginUrl =
            "${Api.MAL_OAUTH2_URL}authorize?response_type=code&client_id=${KtorClient.CLIENT_ID}&code_challenge=${codeVerifier}&state=${STATE}"
    }

    suspend fun getAccessToken(code: String): Response<AccessToken> {
        val accessToken = try {
            api.getAccessToken(
                clientId = KtorClient.CLIENT_ID,
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
            defaultPreferencesRepository.saveTokens(accessToken)
            globalVariables.accessToken = accessToken.accessToken
            Response(data = accessToken)
        }
    }

    suspend fun logOut() {
        defaultPreferencesRepository.removeTokens()
        globalVariables.accessToken = null
        defaultPreferencesRepository.setProfilePicture(null)
    }
}