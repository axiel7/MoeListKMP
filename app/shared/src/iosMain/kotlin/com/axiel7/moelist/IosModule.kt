package com.axiel7.moelist

import com.axiel7.moelist.data.utils.NOTIFICATIONS_DATA_STORE
import com.axiel7.moelist.screens.details.topbar.MediaNotificationsViewModel
import com.axiel7.moelist.screens.more.notifications.NotificationsViewModel
import com.axiel7.moelist.worker.DefaultWorkerFactory
import dev.brewkits.kmpworkmanager.background.data.NativeTaskScheduler
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val iosModule = module {
    single<BackgroundTaskScheduler> { NativeTaskScheduler() }
    factoryOf(::DefaultWorkerFactory)
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