package com.cd.utility.utils

import android.os.Build
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


const val TIME_ZONE_UTC = "UTC"
const val DATE_TIME_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ssX"
const val DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
const val TIME_FORMAT_HH_MM = "HH:mm"
const val DATE_TIME_FORMAT_YYYY_MM_DD_EN = "yyyy/MM/dd"
const val DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
const val DATE_TIME_FORMAT_DD_MM_YY = "ddMMyy"
const val DATE_TIME_FORMAT_DD_MMM_YYYY= "dd MMM, yyyy"
const val DAY_OF_WEEK = "E"

fun String.convertUiFormatToDataFormat(
    inputFormat: String = DATE_TIME_FORMAT_UTC,
    outputFormat: String,
    locale: Locale = Locale.ENGLISH
): String? {
    if (this.isEmpty()) {
        return ""
    }
    val gmtTime = TimeZone.getTimeZone(TIME_ZONE_UTC)
    val sdf = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(inputFormat, locale)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    sdf.timeZone = gmtTime
    val newSdf = SimpleDateFormat(outputFormat, locale)
    newSdf.timeZone = gmtTime
    return try {
        newSdf.format(sdf.parse(this))
    } catch (e: ParseException) {
        null
    }
}

fun String.convertToUTC(
    inputFormat: String = DATE_TIME_FORMAT_UTC,
    outputFormat: String = DATE_TIME_FORMAT_UTC,
    locale: Locale = Locale.ENGLISH
): String? {
    if (TextUtils.isEmpty(this)) {
        return ""
    }
    val gmtTime = TimeZone.getTimeZone(TIME_ZONE_UTC)
    val sdf = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(inputFormat, locale)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    val newSdf = SimpleDateFormat(outputFormat, locale)
    newSdf.timeZone = gmtTime
    return try {
        newSdf.format(sdf.parse(this))
    } catch (e: ParseException) {
        null
    }
}

fun Date.convertUiFormatToDataFormat(
    outputFormat: String,
    locale: Locale = Locale.ENGLISH
): String? {
    val sdf = SimpleDateFormat(outputFormat, locale)
    return try {
        sdf.format(this.time)
    } catch (e: ParseException) {
        null
    }
}

fun Calendar.getDateTime(
    outputFormat: String = TIME_FORMAT_HH_MM,
    locale: Locale = Locale.ENGLISH
): String? {
    return this.time.convertDateToString(outputFormat, locale)
}

fun Calendar.getCurrentDate(
    outputFormat: String = DATE_TIME_FORMAT_YYYY_MM_DD_EN,
    locale: Locale = Locale.ENGLISH
): String? {
    val calendar = Calendar.getInstance(locale)
    return calendar.time.convertDateToString(outputFormat)
}

fun Date.getCurrentDate(
    outputFormat: String = DATE_TIME_FORMAT_YYYY_MM_DD,
    locale: Locale = Locale.ENGLISH
): String? {
    return convertDateToString(outputFormat, locale)
}

fun Date.convertDateToDate(
    outputFormat: String = DATE_TIME_FORMAT_UTC,
    locale: Locale = Locale.ENGLISH
): Date? {
    val df = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(outputFormat, locale)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    return df.format(this).convertStringToDate(outputFormat, locale)
}

fun Date.convertDateToString(
    outputFormat: String = DATE_TIME_FORMAT_UTC,
    locale: Locale = Locale.getDefault()
): String? {
    val df = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(outputFormat, locale)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    return df.format(this)
}

fun String.convertStringToDate(
    outputFormat: String = DATE_TIME_FORMAT_UTC,
    locale: Locale = Locale.ENGLISH

): Date {
    val parser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(outputFormat, locale)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    return try {
        parser.parse(this)
    } catch (e: ParseException) {
        Date()
    }
}

fun Date.getNextDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 1)

    val sdf = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD, Locale.ENGLISH)
    return sdf.format(calendar.time)
}

fun Date.getPreviousDate(format: String = DATE_TIME_FORMAT_YYYY_MM_DD): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return simpleDateFormat.format(calendar.time)
}


fun Date.getDayOfWeek(locale: Locale = Locale.JAPAN): String {
    return this.convertDateToString(DAY_OF_WEEK, locale).toString()
}

fun Date.getDayOfMonth(locale: Locale = Locale.JAPAN): String {
    val calendar = Calendar.getInstance(locale)
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_MONTH).toString()
}

fun Date.getMonthOfYear(locale: Locale = Locale.JAPAN): String {
    val calendar = Calendar.getInstance(locale)
    calendar.time = this
    return (calendar.get(Calendar.MONTH) + 1).toString()
}

fun String.getFirstDayOfWeek(locale: Locale = Locale.JAPAN): Date {
    val calendar = Calendar.getInstance(locale)
    calendar.time = this.convertStringToDate()
    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
        calendar.add(Calendar.DATE, -1)
    }
    return calendar.time
}

fun String.isValidDateFormat(format: String, locale: Locale = Locale.JAPAN): Boolean {
    val formatter = SimpleDateFormat(format, locale)
    formatter.isLenient = false
    return try {
        formatter.parse(this)
        true
    } catch (e: ParseException) {
        false
    }
}

fun Date.isSameDay(expectedDay: Int, locale: Locale = Locale.JAPAN): Boolean {
    val calendar = Calendar.getInstance(locale)
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == expectedDay
}

fun String.convertDateStringWithPlusTime(
    plusTime: Long,
    outputFormat: String = DATE_TIME_FORMAT_UTC,
    locale: Locale = Locale.JAPAN
): String {
    val date = this.convertStringToDate(outputFormat)
    val calendar = Calendar.getInstance(locale)
    calendar.time = date
    return Date(calendar.timeInMillis + plusTime).convertDateToString().toString()
}

fun Date.convertDateWithPlusTime(plusTime: Long, locale: Locale = Locale.JAPAN): Date {
    val calendar = Calendar.getInstance(locale)
    calendar.time = this
    return Date(calendar.timeInMillis + plusTime)
}

fun String.getDaysDifference(date1: String, date2: String = getCurrentDate(DATE_TIME_FORMAT_DD_MM_YY)): Long {
    return try {
        val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT_DD_MM_YY, Locale.ENGLISH)
        val mDate1 = dateFormat.parse(date1)!!
        val mDate2 = dateFormat.parse(date2)!!
        val difference: Long = kotlin.math.abs(mDate1.time - mDate2.time)
        difference / (24 * 60 * 60 * 1000)
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun getCurrentDate(format: String = DATE_TIME_FORMAT_YYYY_MM_DD): String {
    val calendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    return simpleDateFormat.format(calendar.time)
    /*return "2022-07-11"*/
}

fun Long.changeDateFormat(date1: String): String {
    val initDate = SimpleDateFormat(DATE_TIME_FORMAT_DD_MM_YY, Locale.ENGLISH).parse(date1)
    val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH)
    return initDate?.let { dateFormat.format(it) } ?: date1
}


fun String.convertDateToDisplayFormat(myDate: String): String {
    try {
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD, Locale.ENGLISH)
        val date = simpleDateFormat.parse(myDate)
        val simpleDateFormat2 = SimpleDateFormat(DATE_TIME_FORMAT_DD_MMM_YYYY, Locale.ENGLISH)
        return simpleDateFormat2.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return myDate
}

fun String.convertDateToServerFormat(myDate: String): String {
    try {
        //val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT_DD_MMM_YYYY, Locale.ENGLISH)
        val date = simpleDateFormat.parse(myDate)
        val simpleDateFormat2 = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD, Locale.ENGLISH)
        return simpleDateFormat2.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return myDate
}


