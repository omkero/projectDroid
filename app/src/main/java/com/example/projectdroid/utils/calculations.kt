package com.example.projectdroid.utils

// convert GH/s to TH/s
fun GigaHashToTeraHash(num: Float): Double {
    val x = num / 1000.0
    return x
}

fun UptimeToDate(uptime: Int): String {
    val days = uptime / (24 * 3600)
    val hours = (uptime % (24 * 3600)) / 3600
    val minutes = (uptime % 3600) / 60
    val seconds = uptime % 60

    return if (days > 0) {
        "%dd %02dh %02dm %02ds".format(days, hours, minutes, seconds)
    } else {
        "%02dh %02dm %02ds".format(hours, minutes, seconds)
    }
}
