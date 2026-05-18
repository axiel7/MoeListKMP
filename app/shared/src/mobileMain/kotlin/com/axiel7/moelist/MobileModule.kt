package com.axiel7.moelist

import com.axiel7.moelist.data.utils.NOTIFICATIONS_DATA_STORE
import com.axiel7.moelist.worker.NotificationWorkerManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val workerModule = module {
    single { NotificationWorkerManager(get(named(NOTIFICATIONS_DATA_STORE))) }
}