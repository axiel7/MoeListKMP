package com.axiel7.moelist.data.network

import com.axiel7.moelist.data.network.Api.Companion.MAL_OAUTH2_URL
import com.axiel7.moelist.data.utils.AUTH_REDIRECT_URI
import com.axiel7.moelist.data.utils.CLIENT_ID
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.tokenstore.TokenRefreshHandler
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore
import org.publicvalue.multiplatform.oidc.tokenstore.saveTokens
import org.publicvalue.multiplatform.oidc.types.CodeChallengeMethod

@OptIn(ExperimentalOpenIdConnect::class)
class OAuthService(
    val tokenStore: TokenStore,
    private val codeAuthFlowFactory: CodeAuthFlowFactory,
) {
    val client = OpenIdConnectClient {
        endpoints {
            tokenEndpoint = "${MAL_OAUTH2_URL}token"
            authorizationEndpoint = "${MAL_OAUTH2_URL}authorize"
        }

        clientId = CLIENT_ID
        clientSecret = null
        codeChallengeMethod = CodeChallengeMethod.plain
        redirectUri = AUTH_REDIRECT_URI
        disableNonce = true
    }

    private val authFlow get() = codeAuthFlowFactory.createAuthFlow(client)

    val refreshHandler = TokenRefreshHandler(tokenStore = tokenStore)

    suspend fun canContinueLogin() = authFlow.canContinueLogin()

    suspend fun login() {
        authFlow.startLogin()
        val tokens = authFlow.continueLogin()
        tokenStore.saveTokens(tokens)
    }

    suspend fun continueLogin() {
        val tokens = authFlow.continueLogin()
        tokenStore.saveTokens(tokens)
    }

    suspend fun logOut() {
        tokenStore.removeAccessToken()
        tokenStore.removeRefreshToken()
        tokenStore.removeIdToken()
    }
}
