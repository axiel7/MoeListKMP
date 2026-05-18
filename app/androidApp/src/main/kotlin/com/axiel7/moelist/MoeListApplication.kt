package com.axiel7.moelist

import android.app.Application
import android.os.Build
import com.axiel7.moelist.worker.NotificationWorker
import com.axiel7.moelist.worker.NotificationWorkerManager
import com.axiel7.moelist.worker.createAiringAnimeNotificationChannel
import dev.brewkits.kmpworkmanager.KmpWorkManager
import dev.brewkits.kmpworkmanager.background.domain.AndroidWorker
import dev.brewkits.kmpworkmanager.background.domain.AndroidWorkerFactory
import org.koin.android.ext.koin.androidContext
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory

class MoeListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createAiringAnimeNotificationChannel(this)
        }
        initApp(
            context = applicationContext,
            codeAuthFlowFactory = AndroidCodeAuthFlowFactory()
        ).apply {
            androidContext(this@MoeListApplication)
        }
        KmpWorkManager.initialize(
            context = this,
            workerFactory = DefaultWorkerFactory()
        )
    }

    class DefaultWorkerFactory : AndroidWorkerFactory {
        override fun createWorker(workerClassName: String): AndroidWorker {
            return when (workerClassName) {
                NotificationWorkerManager.NOTIFICATION_CLASS_NAME -> NotificationWorker()
                else -> throw IllegalArgumentException("Unregistered worker: $workerClassName")
            }
        }
    }
}
