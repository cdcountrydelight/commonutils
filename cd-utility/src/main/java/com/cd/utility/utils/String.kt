package com.cd.utility.utils

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern
import com.google.common.base.Strings
import java.util.*

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
    val parser = SimpleDateFormat(format, Locale.getDefault())
    return parser.parse(this)
}

fun String?.isBlank(): Boolean {
    return this == null || isEmpty()
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
    val gmtTimeZone = TimeZone.getTimeZone(TIME_ZONE_UTC)
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = gmtTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

@Throws(ParseException::class)
fun String.toDateWithFormat(
    inputFormat: String,
    outputFormat: String,
    outputTimeZone: TimeZone = TimeZone.getDefault()
): String {
    val gmtTimeZone = TimeZone.getTimeZone(TIME_ZONE_UTC)
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = outputTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
    return pattern.matcher(toLowerCase()).find()
}

fun String.validWithPattern(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}

fun String.removeWhitespaces(): String {
    return this.replace("[\\s-]*".toRegex(), "")
}

fun String.toIntOrZero() = if (this.toIntOrNull() == null) 0 else this.toInt()

fun String.isNumeric(): Boolean = this matches "-?\\d+(\\.\\d+)?".toRegex()

fun String.containsWebUrl() = Patterns.WEB_URL.matcher(this).find()

fun String?.nullToEmpty(): String = this ?: ""

fun String?.isNullOrZero() = this == "0" || this == null


inline infix operator fun String.times(n: Int): String = Strings.repeat(this, n)
inline infix operator fun Int.times(s: String): String = Strings.repeat(s, this)

inline fun Double.toFixed(digits: Int): String = java.lang.String.format("%.${digits}f", this)
inline fun Float.toFixed(digits: Int): String = toDouble().toFixed(digits)

inline fun String?.isNotNullOrBlank() = !isNullOrBlank()

inline fun <T> T.toStringTransform(transform: (T) -> String) = let(transform)

inline fun <T> T?.toStringTransform(nullString: String = null.toString(), transform: (T) -> String) =
    letOrElse(nullString, transform)

inline fun String.splitLines() = split('\n')

inline fun <T> T?.wrap() = "$this"
inline fun <T> T?.wrapString() = wrap()

inline fun concat(vararg params: Any?) = params.joinToString("")

inline fun join(vararg params: Any?) = params.joinToString(" ")

inline fun joinWith(separator: String = " ", vararg params: Any?) = params.joinToString(separator)

inline fun String.replace(ignoreCase: Boolean = false, vararg vars: Pair<String, String>): String {
    var copy = this
    vars.forEach { copy = copy.replace(it.first, it.second, ignoreCase) }
    return copy
}

inline fun String.remove(substring: String) = replace(substring, "")

/**
 * Capitalizes first character of every word.
 * Words are separated by one of more space characters.
 */
inline fun String.capitalizeFirstChar() =
    lowercase(Locale.getDefault())
        .split(" +".toRegex())
        .joinToString(" ", "") { d ->
            d.replaceRange(0, 1, d.first().uppercaseChar().toString())
        }

/**
 * Removes non-numerical characters from a string.
 */
fun String.removeNonAlpha() =
    replace("[^a-zA-Z\\s]".toRegex(), " ").replace(" +".toRegex(), " ").trim()

/**
 * Removes duplicates words from a string. Words are separated by one of more space characters.
 * @return String with duplicate words removed
 */
fun String.uniquifyWords(): String {
    val multipleSpacesRegex = " +".toRegex()
    return this.split(multipleSpacesRegex).distinct().joinToString(" ")
}