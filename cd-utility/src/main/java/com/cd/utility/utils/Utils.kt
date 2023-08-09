package com.cd.utility.utils

import android.content.Context
import java.io.IOException

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
}