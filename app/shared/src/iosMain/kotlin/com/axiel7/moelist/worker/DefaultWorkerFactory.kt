package com.axiel7.moelist.worker

import com.axiel7.moelist.data.repository.AnimeRepository
import dev.brewkits.kmpworkmanager.background.data.IosWorker
import dev.brewkits.kmpworkmanager.background.data.IosWorkerFactory
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import dev.brewkits.kmpworkmanager.utils.Logger

class DefaultWorkerFactory(
    private val scheduler: BackgroundTaskScheduler,
    private val animeRepository: AnimeRepository,
    private val notificationWorkerManager: NotificationWorkerManager,
) : IosWorkerFactory {
    override fun createWorker(workerClassName: String): IosWorker? {
        return when (workerClassName) {
            "NotificationWorker" -> NotificationWorker(scheduler, animeRepository, notificationWorkerManager)
            else -> {
                Logger.e("FACTORY", "Unknown worker: $workerClassName")
                null
            }
        }
    }
}