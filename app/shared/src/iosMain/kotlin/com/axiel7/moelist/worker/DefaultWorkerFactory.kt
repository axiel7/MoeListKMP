package com.axiel7.moelist.worker

import dev.brewkits.kmpworkmanager.background.data.IosWorker
import dev.brewkits.kmpworkmanager.background.data.IosWorkerFactory
import dev.brewkits.kmpworkmanager.utils.Logger

class DefaultWorkerFactory : IosWorkerFactory {
    override fun createWorker(workerClassName: String): IosWorker? {
        return when (workerClassName) {
            NotificationWorkerManager.NOTIFICATION_CLASS_NAME -> NotificationWorker()
            else -> {
                Logger.e("KMP_BG_TASK_IOS", "Unknown worker class name: $workerClassName")
                null
            }
        }
    }
}