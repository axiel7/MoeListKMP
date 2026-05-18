package com.axiel7.moelist

import android.app.Application
import android.content.Context
import android.os.Build
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.worker.NotificationWorker
import com.axiel7.moelist.worker.NotificationWorkerManager
import com.axiel7.moelist.worker.createAiringAnimeNotificationChannel
import dev.brewkits.kmpworkmanager.KmpWorkManager
import dev.brewkits.kmpworkmanager.background.domain.AndroidWorker
import dev.brewkits.kmpworkmanager.background.domain.AndroidWorkerFactory
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory

class MoeListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createAiringAnimeNotificationChannel(this)
        }
        val koin = initApp(
            context = applicationContext,
            codeAuthFlowFactory = AndroidCodeAuthFlowFactory()
        ).koin
        KmpWorkManager.initialize(
            context = this,
            workerFactory = DefaultWorkerFactory(
                context = this,
                animeRepository = koin.get(),
                notificationWorkerManager = koin.get(),
            )
        )
    }

    class DefaultWorkerFactory(
        private val context: Context,
        private val animeRepository: AnimeRepository,
        private val notificationWorkerManager: NotificationWorkerManager,
    ): AndroidWorkerFactory {
        override fun createWorker(workerClassName: String): AndroidWorker {
            return when (workerClassName) {
                "NotificationWorker" -> NotificationWorker(context, animeRepository, notificationWorkerManager)
                else -> throw IllegalArgumentException("Unregistered worker: $workerClassName")
            }
        }
    }
}
