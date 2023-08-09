package com.cd.utility.utils

import java.net.NetworkInterface
import java.util.Collections

fun getMacAddress(): String {
    try {
        val all: List<NetworkInterface> =
            Collections.list(NetworkInterface.getNetworkInterfaces())
        for (nif in all) {
            if (!nif.name.equals("wlan0", ignoreCase = true)) continue
            val macBytes: ByteArray = nif.hardwareAddress ?: return ""
            val res1 = StringBuilder()
            for (b in macBytes) {
                res1.append(Integer.toHexString(b.toInt() and 0xFF) + ":")
            }
            if (res1.isNotEmpty()) res1.deleteCharAt(res1.length - 1)
            return res1.toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "02:00:00:00:00:00"
}