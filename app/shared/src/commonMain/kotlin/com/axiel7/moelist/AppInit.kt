package com.axiel7.moelist

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import com.axiel7.moelist.data.local.MoeListDatabase
import com.axiel7.moelist.data.local.getRoomDatabase
import com.axiel7.moelist.data.local.searchhistory.SearchHistoryDao
import com.axiel7.moelist.data.network.Api
import com.axiel7.moelist.data.network.JikanApi
import com.axiel7.moelist.data.network.KtorClient
import com.axiel7.moelist.data.network.OAuthService
import com.axiel7.moelist.data.repository.AnimeRepository
import com.axiel7.moelist.data.repository.DefaultPreferencesRepository
import com.axiel7.moelist.data.repository.LoginRepository
import com.axiel7.moelist.data.repository.MangaRepository
import com.axiel7.moelist.data.repository.SearchHistoryRepository
import com.axiel7.moelist.data.repository.UserRepository
import com.axiel7.moelist.data.utils.DEFAULT_DATA_STORE
import com.axiel7.moelist.data.utils.NOTIFICATIONS_DATA_STORE
import com.axiel7.moelist.main.MainViewModel
import com.axiel7.moelist.screens.calendar.CalendarViewModel
import com.axiel7.moelist.screens.details.MediaDetailsViewModel
import com.axiel7.moelist.screens.editmedia.EditMediaViewModel
import com.axiel7.moelist.screens.home.HomeViewModel
import com.axiel7.moelist.screens.login.LoginViewModel
import com.axiel7.moelist.screens.more.MoreViewModel
import com.axiel7.moelist.screens.more.notifications.NotificationsViewModel
import com.axiel7.moelist.screens.more.settings.SettingsViewModel
import com.axiel7.moelist.screens.more.settings.list.ListStyleSettingsViewModel
import com.axiel7.moelist.screens.profile.ProfileViewModel
import com.axiel7.moelist.screens.ranking.MediaRankingViewModel
import com.axiel7.moelist.screens.recommendations.RecommendationsViewModel
import com.axiel7.moelist.screens.search.SearchViewModel
import com.axiel7.moelist.screens.season.SeasonChartViewModel
import com.axiel7.moelist.screens.userlist.UserMediaListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

@OptIn(ExperimentalOpenIdConnect::class)
fun initApp(
    tokenStore: TokenStore,
    codeAuthFlowFactory: CodeAuthFlowFactory,
    databaseBuilder: RoomDatabase.Builder<MoeListDatabase>,
    createDataStore: (String) -> DataStore<Preferences>,
) {
    startKoin {
        val databaseModule = module {
            single<MoeListDatabase> { getRoomDatabase(databaseBuilder) }
            single<SearchHistoryDao> { get<MoeListDatabase>().searchHistoryDao() }

            singleOf(::SearchHistoryRepository)
        }

        val dataStoreModule = module {
            single(named(DEFAULT_DATA_STORE)) { createDataStore(DEFAULT_DATA_STORE) }
            single(named(NOTIFICATIONS_DATA_STORE)) { createDataStore(NOTIFICATIONS_DATA_STORE) }
        }

        val networkModule = module {
            single { codeAuthFlowFactory }
            single { OAuthService(tokenStore, codeAuthFlowFactory) }
            single { KtorClient(get(), isDebug = false).ktorHttpClient }
            singleOf(::Api)
            singleOf(::JikanApi)
        }

        val repositoryModule = module {
            single { DefaultPreferencesRepository(get(named(DEFAULT_DATA_STORE))) }
            singleOf(::AnimeRepository)
            singleOf(::MangaRepository)
            singleOf(::LoginRepository)
            singleOf(::UserRepository)
        }

        val viewModelModule = module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::SettingsViewModel)
            viewModelOf(::CalendarViewModel)
            viewModelOf(::MediaDetailsViewModel)
            viewModelOf(::EditMediaViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::ListStyleSettingsViewModel)
            viewModel {
                NotificationsViewModel(
                    dataStore = get(named(NOTIFICATIONS_DATA_STORE))
                )
            }
            viewModelOf(::ProfileViewModel)
            viewModelOf(::MediaRankingViewModel)
            viewModelOf(::RecommendationsViewModel)
            viewModelOf(::SearchViewModel)
            viewModelOf(::SeasonChartViewModel)
            viewModel { params ->
                UserMediaListViewModel(
                    mediaType = params.get(),
                    initialListStatus = params.getOrNull(),
                    animeRepository = get(),
                    mangaRepository = get(),
                    defaultPreferencesRepository = get()
                )
            }
            viewModelOf(::MoreViewModel)
        }

        // Note that the order of modules here is significant, later
        // modules can override dependencies from earlier modules
        modules(databaseModule, dataStoreModule, networkModule, repositoryModule, viewModelModule)
    }
}