package com.axiel7.moelist.di

import com.axiel7.moelist.data.utils.NOTIFICATIONS_DATA_STORE
import com.axiel7.moelist.screens.details.topbar.MediaNotificationsViewModel
import com.axiel7.moelist.screens.more.notifications.NotificationsViewModel
import com.axiel7.moelist.worker.DefaultWorkerFactory
import dev.brewkits.kmpworkmanager.KmpWorkManager
import dev.brewkits.kmpworkmanager.background.data.ChainExecutor
import dev.brewkits.kmpworkmanager.background.data.DynamicTaskDispatcher
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val iosModule = module {
    KmpWorkManager.initialize(workerFactory = DefaultWorkerFactory())

    single<BackgroundTaskScheduler> { KmpWorkManager.getInstance().backgroundTaskScheduler }
    single<ChainExecutor> { KmpWorkManager.getInstance().chainExecutor }
    single<DynamicTaskDispatcher> { KmpWorkManager.getInstance().dynamicTaskDispatcher }
}

val iosViewModelModule = module {
    viewModelOf(::MediaNotificationsViewModel)
    viewModel {
        NotificationsViewModel(
            dataStore = get(named(NOTIFICATIONS_DATA_STORE)),
            notificationWorkerManager = get(),
            scheduler = get(),
        )
    }
}