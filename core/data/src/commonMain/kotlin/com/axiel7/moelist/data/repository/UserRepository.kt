package com.axiel7.moelist.data.repository

import com.axiel7.moelist.data.model.Response
import com.axiel7.moelist.data.model.User
import com.axiel7.moelist.data.model.UserStats
import com.axiel7.moelist.data.network.Api
import com.axiel7.moelist.data.network.JikanApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserRepository(
    private val api: Api,
    private val jikanApi: JikanApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    companion object {
        private const val USER_FIELDS = "id,name,gender,location,joined_at,anime_statistics"
    }

    suspend fun getMyUser(): User? = withContext(dispatcher) {
        return@withContext try {
            api.getUser(USER_FIELDS)
        } catch (_: Exception) {
            null
        }
    }

    suspend fun getUserStats(
        username: String
    ): Response<UserStats> = withContext(dispatcher) {
        return@withContext try {
            jikanApi.getUserStats(username)
        } catch (e: Exception) {
            Response(message = e.message)
        }
    }
}