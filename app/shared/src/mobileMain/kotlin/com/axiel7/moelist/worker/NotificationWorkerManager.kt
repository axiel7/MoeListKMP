package com.axiel7.moelist.worker

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.axiel7.moelist.data.model.media.WeekDay
import com.axiel7.moelist.data.utils.DateUtils
import com.axiel7.moelist.data.utils.DateUtils.getNextDayOfWeek
import com.axiel7.moelist.data.utils.SeasonCalendar
import dev.brewkits.kmpworkmanager.background.domain.BackgroundTaskScheduler
import dev.brewkits.kmpworkmanager.background.domain.Constraints
import dev.brewkits.kmpworkmanager.background.domain.ScheduleResult
import dev.brewkits.kmpworkmanager.background.domain.TaskTrigger
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlin.time.Clock

class NotificationWorkerManager(
    private val dataStore: DataStore<Preferences>,
) {
    suspend fun scheduleAiringAnimeNotification(
        title: String,
        animeId: Int,
        weekDay: WeekDay,
        jpHour: LocalTime,
        scheduler: BackgroundTaskScheduler,
    ) {
        val nowJapan = Clock.System.now().toLocalDateTime(SeasonCalendar.japanTimeZone)
        val airingDay = nowJapan.date.getNextDayOfWeek(DayOfWeek(weekDay.ordinal + 1))
        val startDateTime = LocalDateTime(airingDay, jpHour)
            .toInstant(SeasonCalendar.japanTimeZone)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val delay = startDateTime.toInstant(TimeZone.currentSystemDefault()) -
                DateUtils.now().toInstant(TimeZone.currentSystemDefault())

        val inputData = NotificationWorkData(
            animeId = animeId,
            animeTitle = title
        )
        val inputJson = Json.encodeToString(inputData)

        val result = scheduler
            .enqueue(
                id = "notification_$animeId",
                trigger = TaskTrigger.Periodic(
                    intervalMs = ONE_WEEK_MS,
                    initialDelayMs = delay.inWholeMilliseconds,
                ),
                workerClassName = NOTIFICATION_CLASS_NAME,
                constraints = Constraints(requiresNetwork = true),
                inputJson = inputJson
            )
        if (result == ScheduleResult.ACCEPTED) {
            //store notification setting
            dataStore.edit {
                it[stringPreferencesKey(animeId.toString())] = title
            }
        }
    }

    suspend fun removeAiringAnimeNotification(animeId: Int, scheduler: BackgroundTaskScheduler) {
        scheduler.cancel("notification_$animeId")
        dataStore.edit {
            it.remove(stringPreferencesKey(animeId.toString()))
        }
    }

    suspend fun removeStartAiringAnimeNotification(animeId: Int) {
        dataStore.edit {
            it.remove(stringPreferencesKey("start_$animeId"))
        }
    }

    suspend fun scheduleAnimeStartNotification(
        title: String,
        animeId: Int,
        startDate: LocalDate,
        scheduler: BackgroundTaskScheduler
    ) {
        val delay = startDate.atStartOfDayIn(TimeZone.currentSystemDefault()) -
                DateUtils.now().toInstant(TimeZone.currentSystemDefault())

        val inputData = NotificationWorkData(
            animeId = animeId,
            animeTitle = title,
            type = "start",
        )
        val inputJson = Json.encodeToString(inputData)

        val result = scheduler
            .enqueue(
                id = "notification_start_$animeId",
                trigger = TaskTrigger.OneTime(delay.inWholeMilliseconds),
                workerClassName = NOTIFICATION_CLASS_NAME,
                constraints = Constraints(requiresNetwork = true),
                inputJson = inputJson
            )
        if (result == ScheduleResult.ACCEPTED) {
            //store start notification setting
            dataStore.edit {
                it[stringPreferencesKey("start_$animeId")] = title
            }
        }
    }

    suspend fun removeAllNotifications(scheduler: BackgroundTaskScheduler) {
        scheduler.cancelAll()
        dataStore.edit {
            it.clear()
        }
    }

    fun getNotification(mediaId: Int) = dataStore.data.map {
        it[stringPreferencesKey(mediaId.toString())]
    }

    fun getStartNotification(mediaId: Int) = dataStore.data.map {
        it[stringPreferencesKey("start_$mediaId")]
    }

    companion object {
        const val NOTIFICATION_CLASS_NAME = "NotificationWorker"

        const val ONE_WEEK_MS = 604_800_000L
    }
}