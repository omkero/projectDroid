package com.omkero.asicscanner.utils

// convert GH/s to TH/s
fun GigaHashToTeraHash(num: Float): Double {
    val x = num / 1000.0
    return x
}

// convert MH/s to TH/s
fun MigaHashToTeraHash(num: Float): Double {
    val x = num / 1000000.0
    return x
}


fun UptimeToDate(uptime: Double): String {
    val totalSeconds = uptime.toLong()  // drop decimal part
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return if (days > 0) {
        "%dd %02dh %02dm %02ds".format(days, hours, minutes, seconds)
    } else {
        "%02dh %02dm %02ds".format(hours, minutes, seconds)
    }
}
