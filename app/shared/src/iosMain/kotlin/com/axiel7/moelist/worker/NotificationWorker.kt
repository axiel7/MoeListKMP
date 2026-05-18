package com.axiel7.moelist.worker

import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.airing
import dev.brewkits.kmpworkmanager.background.data.IosWorker
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import dev.brewkits.kmpworkmanager.background.domain.WorkerEnvironment
import dev.brewkits.kmpworkmanager.background.domain.WorkerResult
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import platform.Foundation.NSUUID
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

class NotificationWorker(
    private val scheduler: BackgroundTaskScheduler,
    private val animeRepository: AnimeRepository,
    private val notificationWorkerManager: NotificationWorkerManager,
) : IosWorker {
    override suspend fun doWork(input: String?, env: WorkerEnvironment): WorkerResult {
        if (env.isCancelled() || input == null) return WorkerResult.Success()

        try {
            val inputData = Json.decodeFromString<NotificationWorkData>(input)
            val animeId = inputData.animeId

            if (inputData.type == "start")
                notificationWorkerManager.removeStartAiringAnimeNotification(animeId)

            // remove periodic worker if anime ended
            val animeDetails = animeRepository.getAnimeAiringStatus(animeId)
            if (animeDetails?.status != MediaStatus.AIRING) {
                notificationWorkerManager.removeAiringAnimeNotification(animeId, scheduler)
                return WorkerResult.Success()
            }

            val content = UNMutableNotificationContent().apply {
                setTitle(getString(UiRes.string.airing))
                setBody(inputData.animeTitle)
            }

            val request = UNNotificationRequest.requestWithIdentifier(
                identifier = NSUUID().UUIDString(),
                content = content,
                trigger = null
            )

            UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) {
                print(it)
            }

            return WorkerResult.Success()
        } catch (e: Exception) {
            return WorkerResult.Failure(e.message.orEmpty())
        }
    }
}