package com.cd.utility.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.*
import java.util.Date
import java.util.concurrent.TimeUnit
import java.io.PrintStream
import java.math.MathContext
import java.time.Duration
import java.util.Calendar
import java.util.Locale
import kotlin.system.measureNanoTime

object ago
object fromNow

val since = fromNow
val later = fromNow

private val agoRef = ago
private val fromNowRef = fromNow

data class PeriodDuration internal constructor(val period: Period, val duration: Duration)

val PeriodDuration.ago: LocalDateTime @RequiresApi(Build.VERSION_CODES.O)
get() = Dates.of(period, duration, agoRef)
val PeriodDuration.fromNow: LocalDateTime @RequiresApi(Build.VERSION_CODES.O)
get() = Dates.of(period, duration, fromNowRef)
val PeriodDuration.since: LocalDateTime @RequiresApi(Build.VERSION_CODES.O)
get() = Dates.of(period, duration, fromNowRef)
val PeriodDuration.later: LocalDateTime @RequiresApi(Build.VERSION_CODES.O)
get() = Dates.of(period, duration, fromNowRef)

object Dates {

    @RequiresApi(Build.VERSION_CODES.O)
    fun of(date: LocalDate, time: LocalTime): LocalDateTime = LocalDateTime.of(date, time)

    fun of(date: Period, time: Duration): PeriodDuration = PeriodDuration(date, time)
    @RequiresApi(Build.VERSION_CODES.O)
    fun of(date: LocalDate, time: Duration): PeriodDuration = of(date.period, time)
    @RequiresApi(Build.VERSION_CODES.O)
    fun of(date: Period, time: LocalTime): PeriodDuration = of(date, time.duration)

    @RequiresApi(Build.VERSION_CODES.O)
    fun of(date: Period, time: Duration, fromNow: fromNow): LocalDateTime = of(date.fromNow.toLocalDate(), time.fromNow.toLocalTime())
    @RequiresApi(Build.VERSION_CODES.O)
    fun of(date: Period, time: Duration, ago: ago): LocalDateTime = of(date.ago.toLocalDate(), time.ago.toLocalTime())

}

@RequiresApi(Build.VERSION_CODES.O)
inline fun now() = LocalDateTime.now()

@RequiresApi(Build.VERSION_CODES.O)
inline fun time() = LocalTime.now()
@RequiresApi(Build.VERSION_CODES.O)
inline fun nextMinute() = time() + 1.minute
@RequiresApi(Build.VERSION_CODES.O)
inline fun nextHour() = time() + 1.hour
@RequiresApi(Build.VERSION_CODES.O)
inline fun lastMinute() = time() - 1.minute
@RequiresApi(Build.VERSION_CODES.O)
inline fun lastHour() = time() - 1.hour

@RequiresApi(Build.VERSION_CODES.O)
inline fun date() = LocalDate.now()
@RequiresApi(Build.VERSION_CODES.O)
inline fun today() = date()

@RequiresApi(Build.VERSION_CODES.O)
inline fun tomorrow() = today() + 1.day
@RequiresApi(Build.VERSION_CODES.O)
inline fun nextWeek() = today() + 1.week
@RequiresApi(Build.VERSION_CODES.O)
inline fun nextMonth() = today() + 1.month
@RequiresApi(Build.VERSION_CODES.O)
inline fun nextYear() = today() + 1.year
@RequiresApi(Build.VERSION_CODES.O)
inline fun yesterday() = today() - 1.day
@RequiresApi(Build.VERSION_CODES.O)
inline fun lastWeek() = today() - 1.week
@RequiresApi(Build.VERSION_CODES.O)
inline fun lastMonth() = today() - 1.month
@RequiresApi(Build.VERSION_CODES.O)
inline fun lastYear() = today() - 1.year

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.destructured(): Pair<LocalDate, LocalTime> = toLocalDate() to toLocalTime()

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.toDate(zoneId: ZoneId = ZoneId.systemDefault()): Date = Date.from(atZone(zoneId).toInstant())
@RequiresApi(Build.VERSION_CODES.O)
inline fun Date.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zoneId)

@RequiresApi(Build.VERSION_CODES.O)
inline fun String.parseDate(formatter: SimpleDateFormat = SimpleDateFormat(), zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime = formatter.parse(this).toLocalDateTime(zoneId)
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.format(formatter: SimpleDateFormat = SimpleDateFormat(), zoneId: ZoneId = ZoneId.systemDefault()): String = formatter.format(toDate(zoneId))

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isMonday() = dayOfWeek == DayOfWeek.MONDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isTuesday() = dayOfWeek == DayOfWeek.TUESDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isWednesday() = dayOfWeek == DayOfWeek.WEDNESDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isThursday() = dayOfWeek == DayOfWeek.THURSDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isFriday() = dayOfWeek == DayOfWeek.FRIDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isSaturday() = dayOfWeek == DayOfWeek.SATURDAY
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isSunday() = dayOfWeek == DayOfWeek.SUNDAY

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isWeekend() = isSaturday() or isSunday()
@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDateTime.isWeekday() = !isWeekend()

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDate.atTime(time: LocalDateTime): LocalDateTime = atTime(time.toLocalTime())

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalDate.atTime(duration: Duration): LocalDateTime {
    val hours = duration.toHours()
    val hour = maxOf(hours, 24).toInt()
    var aux = duration.minusHours(hours)
    val minutes = aux.toMinutes()
    val minute = minutes.toInt()
    aux = aux.minusMinutes(minutes)
    val second = aux.seconds.toInt()
    return atTime(hour, minute, second, aux.nano)
}

@RequiresApi(Build.VERSION_CODES.O)
inline fun LocalTime.atDate(date: LocalDateTime): LocalDateTime = atDate(date.toLocalDate())

inline val LocalDate.period: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = Period.of(year, monthValue, dayOfMonth)
inline val LocalTime.duration: Duration @RequiresApi(Build.VERSION_CODES.O)
get() = toNanoOfDay().nanoseconds

inline val LocalDate.fromNow: LocalDateTime @RequiresApi(Build.VERSION_CODES.O)
get() = atTime(now())
inline val LocalDate.since: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow
inline val LocalDate.later: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow

inline val LocalTime.fromNow: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = atDate(now())
inline val LocalTime.since: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow
inline val LocalTime.later: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow

@RequiresApi(Build.VERSION_CODES.O)
inline operator fun Period.plus(dateTime: LocalDateTime): LocalDateTime = dateTime + this
@RequiresApi(Build.VERSION_CODES.O)
inline operator fun Duration.plus(dateTime: LocalDateTime): LocalDateTime = dateTime + this

inline val Duration.ago: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = now() - this
inline val Duration.fromNow: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = now() + this
inline val Duration.since: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow
inline val Duration.later: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow

inline val Period.ago: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = now() - this
inline val Period.fromNow: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = now() + this
inline val Period.since: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow
inline val Period.later: LocalDateTime
@RequiresApi(Build.VERSION_CODES.O)
get() = fromNow

inline val Long.nanoseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Duration::ofNanos)
inline val Long.nanosecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = nanoseconds

inline val Long.microseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = Duration.ofNanos(this * 1000L)
inline val Long.microsecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = microseconds

inline val Long.milliseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Duration::ofMillis)
inline val Long.millisecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = milliseconds

inline val Long.seconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Duration::ofSeconds)
inline val Long.second: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = seconds

inline val Long.minutes: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Duration::ofMinutes)
inline val Long.minute: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = minutes

inline val Long.hours: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Duration::ofHours)
inline val Long.hour: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = hours

inline val Long.daysDuration: Duration @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().daysDuration
inline val Long.dayDuration: Duration @RequiresApi(Build.VERSION_CODES.O)
get() = daysDuration

inline val Long.weeksDuration: Duration @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().weeksDuration
inline val Long.weekDuration: Duration @RequiresApi(Build.VERSION_CODES.O)
get() = weeksDuration

inline val Long.days: Period @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().days
inline val Long.day: Period @RequiresApi(Build.VERSION_CODES.O)
get() = days

inline val Long.weeks: Period @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().weeks
inline val Long.week: Period @RequiresApi(Build.VERSION_CODES.O)
get() = weeks

inline val Long.months: Period @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().months
inline val Long.month: Period @RequiresApi(Build.VERSION_CODES.O)
get() = months

inline val Long.years: Period @RequiresApi(Build.VERSION_CODES.O)
get() = toInt().years
inline val Long.year: Period @RequiresApi(Build.VERSION_CODES.O)
get() = years

inline val Int.nanoseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().nanoseconds
inline val Int.nanosecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = nanoseconds

inline val Int.microseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().microseconds
inline val Int.microsecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = microseconds

inline val Int.milliseconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().milliseconds
inline val Int.millisecond: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = milliseconds

inline val Int.seconds: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().seconds
inline val Int.second: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = seconds

inline val Int.minutes: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().minutes
inline val Int.minute: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = minutes

inline val Int.hours: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = toLong().hours
inline val Int.hour: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = hours

inline val Int.daysDuration: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = (24 * this).hours
inline val Int.dayDuration: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = daysDuration

inline val Int.weeksDuration: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = (24 * 7 * this).hours
inline val Int.weekDuration: Duration
@RequiresApi(Build.VERSION_CODES.O)
get() = weeksDuration

inline val Int.days: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Period::ofDays)
inline val Int.day: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = days

inline val Int.weeks: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Period::ofWeeks)
inline val Int.week: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = weeks

inline val Int.months: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Period::ofMonths)
inline val Int.month: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = months

inline val Int.years: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = run(Period::ofYears)
inline val Int.year: Period
@RequiresApi(Build.VERSION_CODES.O)
get() = years

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.nanoseconds(fromNow: fromNow): LocalDateTime = now().plusNanos(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.nanoseconds(ago: ago): LocalDateTime = now().minusNanos(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.microseconds(fromNow: fromNow): LocalDateTime = now().plusNanos(times(1000L))
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.microseconds(ago: ago): LocalDateTime = now().minusNanos(times(1000L))

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.milliseconds(fromNow: fromNow): LocalDateTime = now().plusNanos(times(1_000_000L))
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.milliseconds(ago: ago): LocalDateTime = now().minusNanos(times(1_000_000L))

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.seconds(fromNow: fromNow): LocalDateTime = now().plusSeconds(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.seconds(ago: ago): LocalDateTime = now().minusSeconds(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.minutes(fromNow: fromNow): LocalDateTime = now().plusMinutes(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.minutes(ago: ago): LocalDateTime = now().minusMinutes(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.hours(fromNow: fromNow): LocalDateTime = now().plusHours(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.hours(ago: ago): LocalDateTime = now().minusHours(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.days(fromNow: fromNow): LocalDateTime = now().plusDays(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.days(ago: ago): LocalDateTime = now().minusDays(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.weeks(fromNow: fromNow): LocalDateTime = now().plusWeeks(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.weeks(ago: ago): LocalDateTime = now().minusWeeks(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.months(fromNow: fromNow): LocalDateTime = now().plusMonths(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.months(ago: ago): LocalDateTime = now().minusMonths(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.years(fromNow: fromNow): LocalDateTime = now().plusYears(this)
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.years(ago: ago): LocalDateTime = now().minusYears(this)

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.nanosecond(fromNow: fromNow): LocalDateTime = this nanoseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.nanosecond(ago: ago): LocalDateTime = this nanoseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.microsecond(fromNow: fromNow): LocalDateTime = this microseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.microsecond(ago: ago): LocalDateTime = this microseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.millisecond(fromNow: fromNow): LocalDateTime = this milliseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.millisecond(ago: ago): LocalDateTime = this milliseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.second(fromNow: fromNow): LocalDateTime = this seconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.second(ago: ago): LocalDateTime = this seconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.minute(fromNow: fromNow): LocalDateTime = this minutes fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.minute(ago: ago): LocalDateTime = this minutes ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.hour(fromNow: fromNow): LocalDateTime = this hours fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.hour(ago: ago): LocalDateTime = this hours ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.day(fromNow: fromNow): LocalDateTime = this days fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.day(ago: ago): LocalDateTime = this days ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.week(fromNow: fromNow): LocalDateTime = this weeks fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.week(ago: ago): LocalDateTime = this weeks ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.month(fromNow: fromNow): LocalDateTime = this months fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.month(ago: ago): LocalDateTime = this months ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.year(fromNow: fromNow): LocalDateTime = this years fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Long.year(ago: ago): LocalDateTime = this years ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.nanoseconds(fromNow: fromNow): LocalDateTime = toLong() nanoseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.nanoseconds(ago: ago): LocalDateTime = toLong() nanoseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.microseconds(fromNow: fromNow): LocalDateTime = toLong() microseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.microseconds(ago: ago): LocalDateTime = toLong() microseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.milliseconds(fromNow: fromNow): LocalDateTime = toLong() milliseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.milliseconds(ago: ago): LocalDateTime = toLong() milliseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.seconds(fromNow: fromNow): LocalDateTime = toLong() seconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.seconds(ago: ago): LocalDateTime = toLong() seconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.minutes(fromNow: fromNow): LocalDateTime = toLong() minutes fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.minutes(ago: ago): LocalDateTime = toLong() minutes ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.hours(fromNow: fromNow): LocalDateTime = toLong() hours fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.hours(ago: ago): LocalDateTime = toLong() hours ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.days(fromNow: fromNow): LocalDateTime = toLong() days fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.days(ago: ago): LocalDateTime = toLong() days ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.weeks(fromNow: fromNow): LocalDateTime = toLong() weeks fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.weeks(ago: ago): LocalDateTime = toLong() weeks ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.months(fromNow: fromNow): LocalDateTime = toLong() months fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.months(ago: ago): LocalDateTime = toLong() months ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.years(fromNow: fromNow): LocalDateTime = toLong() years fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.years(ago: ago): LocalDateTime = toLong() years ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.nanosecond(fromNow: fromNow): LocalDateTime = toLong() nanoseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.nanosecond(ago: ago): LocalDateTime = toLong() nanoseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.microsecond(fromNow: fromNow): LocalDateTime = toLong() microseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.microsecond(ago: ago): LocalDateTime = toLong() microseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.millisecond(fromNow: fromNow): LocalDateTime = toLong() milliseconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.millisecond(ago: ago): LocalDateTime = toLong() milliseconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.second(fromNow: fromNow): LocalDateTime = toLong() seconds fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.second(ago: ago): LocalDateTime = toLong() seconds ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.minute(fromNow: fromNow): LocalDateTime = toLong() minutes fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.minute(ago: ago): LocalDateTime = toLong() minutes ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.hour(fromNow: fromNow): LocalDateTime = toLong() hours fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.hour(ago: ago): LocalDateTime = toLong() hours ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.day(fromNow: fromNow): LocalDateTime = toLong() days fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.day(ago: ago): LocalDateTime = toLong() days ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.week(fromNow: fromNow): LocalDateTime = toLong() weeks fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.week(ago: ago): LocalDateTime = toLong() weeks ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.month(fromNow: fromNow): LocalDateTime = toLong() months fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.month(ago: ago): LocalDateTime = toLong() months ago

@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.year(fromNow: fromNow): LocalDateTime = toLong() years fromNow
@RequiresApi(Build.VERSION_CODES.O)
inline infix fun Int.year(ago: ago): LocalDateTime = toLong() years ago

open class TimeUnitFormatter protected constructor(val day: String,
                                                   val days: String = day,
                                                   val hour: String,
                                                   val hours: String = day,
                                                   val minute: String,
                                                   val minutes: String = minute,
                                                   val second: String,
                                                   val seconds: String = second,
                                                   val millisecond: String,
                                                   val milliseconds: String = millisecond,
                                                   val microsecond: String,
                                                   val microseconds: String = microsecond,
                                                   val nanosecond: String,
                                                   val nanoseconds: String = nanosecond) {

    companion object Defaults {
        val SHORT by lazy { TimeUnitFormatter(day = "d", hour = "h", minute = "m", second = "s", millisecond = "ms", microsecond = "us", nanosecond = "ns") }
        val LONG by lazy { TimeUnitFormatter("day", "days", "hour", "hours", "minute", "minutes", "second", "seconds", "millisecond", "milliseconds", "microsecond", "microseconds", "nanosecond", "nanoseconds") }
    }

    open fun format(value: Long, unit: TimeUnit) = "$value ${get(value, unit)} "

    open fun formatLast(value: Number, unit: TimeUnit) = format(value.toLong(), unit)

    protected fun get(value: Long, unit: TimeUnit): String {
        val isPlural = Math.abs(value) != 1L

        fun get(singular: String, plural: String) = if (isPlural) plural else singular

        return when (unit) {
            TimeUnit.DAYS -> get(day, days)
            TimeUnit.HOURS -> get(hour, hours)
            TimeUnit.MINUTES -> get(minute, minutes)
            TimeUnit.SECONDS -> get(second, seconds)
            TimeUnit.MILLISECONDS -> get(millisecond, milliseconds)
            TimeUnit.MICROSECONDS -> get(microsecond, microseconds)
            TimeUnit.NANOSECONDS -> get(nanosecond, nanoseconds)
        }
    }

}

private val NANO_MICRO by lazy { 1000L }
private val NANO_MILLI by lazy { 1000 * NANO_MICRO }
private val NANO_SECOND by lazy { 1000 * NANO_MILLI }
private val NANO_MINUTE by lazy { 60 * NANO_SECOND }
private val NANO_HOUR by lazy { 60 * NANO_MINUTE }
private val NANO_DAY by lazy { 24 * NANO_HOUR }

inline fun millis() = System.currentTimeMillis()

inline fun nanos() = System.nanoTime()

object Durations {

    @RequiresApi(Build.VERSION_CODES.O)
    fun between(start: Long, end: Long, unit: TimeUnit) = betweenNanos(unit.toNanos(start), unit.toNanos(end))

    @RequiresApi(Build.VERSION_CODES.O)
    fun betweenNanos(start: Long, end: Long) = Duration.ofNanos(end - start)

    @RequiresApi(Build.VERSION_CODES.O)
    fun betweenMillis(start: Long, end: Long) = Duration.ofMillis(end - start)

    /**
     * Duration from [ago] to current [millis()][millis], so the precision is of milliseconds even if [unit] == [TimeUnit.NANOSECONDS]
     *
     * For nanosecond precision use [betweenNanos] with a proper start point in nanoseconds, or [fromSystemNanos] if you are measuring from [nanos]
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun from(ago: Long, unit: TimeUnit) = Duration.ofMillis(millis() - unit.toMillis(ago))

    /** Duration from [ago] to current [millis()][millis] */
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromMillis(ago: Long) = Duration.ofMillis(millis() - ago)

    /** Use this method only if [nanos] is extracted from [nanos()][me.carleslc.kotlin.extensions.time.nanos], otherwise resulting duration will be unexpected */
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromSystemNanos(nanos: Long): Duration = betweenNanos(nanos, nanos())

}

@RequiresApi(Build.VERSION_CODES.O)
inline fun measure(block: () -> Unit): Duration = measureNanoTime(block).nanoseconds

@RequiresApi(Build.VERSION_CODES.O)
inline fun <T> measureAndReturn(block: () -> T): Pair<Duration, T> {
    val start = System.nanoTime()
    val ret = block()
    val duration = (System.nanoTime() - start).nanoseconds
    return duration to ret
}

@RequiresApi(Build.VERSION_CODES.O)
inline fun <T> measureAndPrint(limit: TimeUnit = TimeUnit.NANOSECONDS,
                               formatter: TimeUnitFormatter = TimeUnitFormatter.LONG,
                               roundingLast: MathContext = MathContext(1),
                               noinline transformation: ((String) -> String)? = null,
                               outputStream: PrintStream = System.out,
                               block: () -> T): T {
    measureAndReturn(block).let {
        it.first.humanize(limit, formatter, roundingLast, transformation).run(outputStream::println)
        return it.second
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Duration.humanize(limit: TimeUnit = TimeUnit.NANOSECONDS, formatter: TimeUnitFormatter = TimeUnitFormatter.LONG, roundingLast: MathContext = MathContext(1), transformation: ((String) -> String)? = null): String {
    val builder = StringBuilder()
    var nanos = abs().toNanos()
    var finished = false

    fun intermediateAppend(threshold: Long, unit: TimeUnit) {
        if (nanos > threshold) {
            val value = if (threshold > 0.0) nanos / threshold else nanos
            builder.append(formatter.format(value, unit))
            nanos %= threshold
        }
    }

    fun lastAppend(threshold: Long, unit: TimeUnit) {
        if (nanos > threshold || builder.isEmpty()) {
            val value = if (threshold > 0.0) nanos.roundDiv(threshold, roundingLast).toLong() else nanos
            builder.append(formatter.formatLast(value, unit))
        }
        finished = true
    }

    fun append(step: TimeUnit, threshold: Long) {
        if (!finished) {
            when (step) {
                limit -> lastAppend(threshold, step)
                else -> intermediateAppend(threshold, step)
            }
        }
    }

    append(TimeUnit.DAYS, NANO_DAY)
    append(TimeUnit.HOURS, NANO_HOUR)
    append(TimeUnit.MINUTES, NANO_MINUTE)
    append(TimeUnit.SECONDS, NANO_SECOND)
    append(TimeUnit.MILLISECONDS, NANO_MILLI)
    append(TimeUnit.MICROSECONDS, NANO_MICRO)
    append(TimeUnit.NANOSECONDS, 0L)

    return builder.toString().trim().with(transformation)
}

fun getCurrentTime(): String {
    val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH)
    return simpleDateFormat.format(Date())
}

fun Long.getHoursDifference(date1: String, date2: String = getCurrentTime()): Long {
    return try {
        val newDate1 = changeDateFormat(date1)
        val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH)
        val mDate1 = dateFormat.parse(newDate1) ?: return 0
        val mDate2 = dateFormat.parse(date2) ?: return 0
        val difference: Long = kotlin.math.abs(mDate1.time - mDate2.time)
        difference / (60 * 60 * 1000)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun Pair<Long, String>.getMinuteDifference(orderTime: String): Pair<Long, String> {
    return try {
        val currentTime = Calendar.getInstance().timeInMillis
        val orderTimeMs =
            SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH).parse(orderTime)
        if (currentTime < (orderTimeMs?.time ?: currentTime))
            return 0L to " mins ago"
        val diff: Long = currentTime - (orderTimeMs?.time ?: currentTime)
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> days to " days ago"
            hours > 1 -> hours to " hrs ago"
            hours > 0 -> hours to " hr ago"
            minutes > 0 -> minutes to " mins ago"
            else -> minutes to " mins ago"
        }

    } catch (e: Exception) {
        e.printStackTrace()
        0L to " mins ago"
    }
}

fun getTimeStamp() = System.currentTimeMillis()

fun isTimeBefore30Min(updatedTime: Long?): Boolean {
    val timeBefore30min = getTimeStamp() - (30 * 60 * 1000)
    return updatedTime == null || (timeBefore30min >= updatedTime && updatedTime > 0)
}
