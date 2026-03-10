package com.axiel7.moelist.data.network

import com.axiel7.moelist.data.utils.CLIENT_ID
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.ktor.oidcBearer

class KtorClient(
    oAuthService: OAuthService,
    isDebug: Boolean,
) {
    @OptIn(ExperimentalOpenIdConnect::class)
    val ktorHttpClient = HttpClient {

        expectSuccess = false

        install(ContentNegotiation) {
            json(
                Json {
                    coerceInputValues = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpCache)

        if (isDebug) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }

        install(Auth) {
            oidcBearer(
                tokenStore = oAuthService.tokenStore,
                refreshHandler = oAuthService.refreshHandler,
                client = oAuthService.client,
            )
        }

        install(DefaultRequest) {
            header("X-MAL-CLIENT-ID", CLIENT_ID)
        }
    }
}