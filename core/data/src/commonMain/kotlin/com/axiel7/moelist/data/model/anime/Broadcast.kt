package com.axiel7.moelist.data.model.anime

import androidx.compose.runtime.Composable
import com.axiel7.moelist.data.model.media.WeekDay
import com.axiel7.moelist.data.utils.DateUtils
import com.axiel7.moelist.data.utils.DateUtils.getNextDayOfWeek
import com.axiel7.moelist.data.utils.DateUtils.secondsToLegibleText
import com.axiel7.moelist.data.utils.SeasonCalendar
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ago
import com.axiel7.moelist.ui.generated.resources.aired_ago
import com.axiel7.moelist.ui.generated.resources.airing_in
import com.axiel7.moelist.ui.generated.resources.unknown
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import kotlin.math.absoluteValue
import kotlin.time.Clock

@Serializable
data class Broadcast(
    @SerialName("day_of_the_week")
    val dayOfTheWeek: WeekDay? = null,
    @SerialName("start_time")
    val startTime: String? = null
) {

    // Custom formatters for kotlinx-datetime (since java.time formatters are JVM-only)
    private val timeTextFormat = LocalDateTime.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        char(' ')
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
    }

    private val localTimeFormat = LocalTime.Format {
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
    }

    @Composable
    fun timeText(isAiring: Boolean) = buildString {
        val firstText = when {
            dayOfTheWeek != null && startTime != null -> {
                dateTimeUntilNextBroadcast()?.format(timeTextFormat)
            }
            dayOfTheWeek != null -> dayOfTheWeek.localized()
            startTime != null -> "$startTime (JST)"
            else -> null
        }

        if (firstText != null) {
            append(firstText)
            if (isAiring) {
                val airingIn = airingInString()
                if (airingIn.isNotEmpty()) {
                    append("\n$airingIn")
                }
            }
        } else {
            append(stringResource(UiRes.string.unknown))
        }
    }

    fun localStartTime() = runCatching {
        dateTimeUntilNextBroadcast()?.time?.format(localTimeFormat)
    }.getOrNull()

    fun localDayOfTheWeek() = runCatching {
        dateTimeUntilNextBroadcast()?.dayOfWeek
    }.getOrNull()

    @Composable
    fun airingInString() = if (startTime != null && dayOfTheWeek != null) {
        val remaining = remaining()
        if (remaining > 0) {
            stringResource(UiRes.string.airing_in, remaining.secondsToLegibleText())
        } else stringResource(UiRes.string.aired_ago, remaining.absoluteValue.secondsToLegibleText())
    } else ""

    @Composable
    fun airingInShortString() = if (startTime != null && dayOfTheWeek != null) {
        val remaining = remaining()
        if (remaining > 0) remaining.secondsToLegibleText()
        else stringResource(UiRes.string.ago, remaining.absoluteValue.secondsToLegibleText())
    } else ""

    // Note: If you need specialized patterns like "EE, d MMM HH:mm" across KMP,
    // it's recommended to implement a platform-specific expectation or use a helper.
    fun nextAiringDayFormatted() = runCatching {
        dateTimeUntilNextBroadcast()?.toString() // Fallback to ISO or custom format
    }.getOrNull()

    private fun remaining() =
        secondsUntilNextBroadcast() - Clock.System.now().epochSeconds

    fun secondsUntilNextBroadcast() =
        dateTimeUntilNextBroadcast()?.toInstant(DateUtils.defaultTimeZone)?.epochSeconds ?: 0

    private fun dateTimeUntilNextBroadcast(): LocalDateTime? =
        if (startTime != null && dayOfTheWeek != null) {
            val now =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val airingDay = now.date.getNextDayOfWeek(DayOfWeek(dayOfTheWeek.ordinal + 1))
            val airingTime = LocalTime.parse(startTime)

            val localDateTime = LocalDateTime(airingDay, airingTime)

            // Convert from Japan time to local time
            localDateTime.toInstant(SeasonCalendar.japanTimeZone)
                .toLocalDateTime(TimeZone.currentSystemDefault())
        } else null
}