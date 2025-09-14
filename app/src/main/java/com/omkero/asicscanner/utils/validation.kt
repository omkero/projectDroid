package com.omkero.asicscanner.utils

fun ValidIpv4(ipv4: String) : Boolean {
    val ipv4Regex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")

    return  ipv4.matches(ipv4Regex)
}