package com.cd.utility.utils

import android.content.Context
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun loadJSONFromAsset(context: Context, jsonFileName: String): String? {
        val json: String = try {
            val `is` = context.assets.open(jsonFileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getCurrentDate(format: String = "yyyy-MM-dd"): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        val todayDate = simpleDateFormat.format(calendar.time)
        return todayDate
    }

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
}