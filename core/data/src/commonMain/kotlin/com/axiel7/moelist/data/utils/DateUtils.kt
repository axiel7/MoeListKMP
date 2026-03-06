package com.axiel7.moelist.data.utils

import androidx.compose.runtime.Composable
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.hour_abbreviation
import com.axiel7.moelist.ui.generated.resources.minutes_abbreviation
import com.axiel7.moelist.ui.generated.resources.num_days
import com.axiel7.moelist.ui.generated.resources.num_months
import com.axiel7.moelist.ui.generated.resources.num_weeks
import com.axiel7.moelist.ui.generated.resources.num_years
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

object DateUtils {

    val defaultTimeZone: TimeZone get() = TimeZone.currentSystemDefault()

    // kotlinx-datetime equivalent of FormatStyle.MEDIUM (roughly)
    private val mediumDateFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        day(padding = Padding.NONE)
        char(',')
        char(' ')
        year()
    }

    /**
     * @return the date in LocalDate, null if fails
     */
    fun getLocalDateFromDateString(
        date: String?
    ): LocalDate? = runCatching {
        if (date == null) return null
        LocalDate.parse(date) // ISO_DATE is the default parse
    }.getOrNull()

    /**
     * @return the date in LocalDate, null if fails
     */
    fun getLocalDateFromMillis(millis: Long): LocalDate? = runCatching {
        Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC).date
    }.getOrNull()

    fun LocalDate?.toLocalized(): String = runCatching {
        // Since custom locales aren't in commonMain yet, we use a standard Format
        this?.format(mediumDateFormat).orEmpty()
    }.getOrElse { "" }

    fun String.parseDate(): LocalDate? = runCatching {
        LocalDate.parse(this)
    }.getOrNull()

    fun String.parseDateAndLocalize(
        format: DateTimeFormat<LocalDate> = LocalDate.Formats.ISO
    ): String? = runCatching {
        val dashCount = this.count { it == '-' }
        when (dashCount) {
            0 -> this // Only year
            1 -> this // Year and month (e.g. 2007-11)
            else -> {
                LocalDate.parse(input = this, format = format).format(mediumDateFormat)
            }
        }
    }.getOrNull()

    fun LocalDate.toEpochMillis(
        timeZone: TimeZone = defaultTimeZone
    ) = this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    /**
     * Replacement for TemporalAdjusters.nextOrSame
     */
    fun LocalDate.getNextDayOfWeek(dayOfWeek: DayOfWeek): LocalDate {
        var result = this
        // Adjust for 1-based indexing (Monday=1, Sunday=7)
        while (result.dayOfWeek != dayOfWeek) {
            result = result.plus(1, DateTimeUnit.DAY)
        }
        return result
    }

    /**
     * Converts seconds to years, months, weeks, days, hours or minutes.
     * Depending if there is enough time.
     * Eg. If days greater than 1 and less than 6, returns "x days"
     */
    @Composable
    fun Long.secondsToLegibleText(): String {
        val days = (this / 86400).toInt()
        return if (days > 6) {
            val weeks = (this / 604800).toInt()
            if (weeks > 4) {
                val months = (this / 2629746).toInt()
                if (months > 12) {
                    val years = (this / 31556952).toInt()
                    pluralStringResource(UiRes.plurals.num_years, years, years)
                } else pluralStringResource(UiRes.plurals.num_months, months, months)
            } else pluralStringResource(UiRes.plurals.num_weeks, weeks, weeks)
        } else if (days >= 1) pluralStringResource(UiRes.plurals.num_days, days, days)
        else {
            val hours = this / 3600
            if (hours >= 1) "$hours ${stringResource(UiRes.string.hour_abbreviation)}"
            else {
                val minutes = (this % 3600) / 60
                "$minutes ${stringResource(UiRes.string.minutes_abbreviation)}"
            }
        }
    }
}