package com.omkero.asicscanner.repo

import android.util.Log
import com.omkero.asicscanner.utils.GigaHashToTeraHash
import com.omkero.asicscanner.utils.MigaHashToTeraHash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


fun fetchWhatsminerDataLoop(
    scope: CoroutineScope,
    ipv4: String,
    port: String,
    onHashRate: (Double) -> Unit,
    onTemp: (String) -> Unit,
    onUptime: (Double) -> Unit,
    onFan1: (String) -> Unit,
    onFan2: (String) -> Unit,
    onFan3: (String) -> Unit,
    onFan4: (String) -> Unit,
    onPool1: (String) -> Unit,
    onPool2: (String) -> Unit,
    onPool3: (String) -> Unit,
    onError: () -> Unit,
    onIsPool1Alive: (String) -> Unit,
    onIsPool2Alive: (String) -> Unit,
    onIsPool3Alive: (String) -> Unit,
    onPoolUser1: (String) -> Unit,
    onPoolUser2: (String) -> Unit,
    onPoolUser3: (String) -> Unit,
    onDeviceName: (String) -> Unit

) {
    scope.launch {
        while (isActive) {
            try {
                // ----------- 1) Fetch stats -----------
                val edevsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"edevs\"}")
                    reader.readLine()
                } ?: ""

                // ----------- 2) Fetch pools -----------
                val poolsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"pools\"}")
                    reader.readLine()
                } ?: ""

                // ----------- 3) Fetch summary -----------
                val summaryJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"summary\"}")
                    reader.readLine()
                } ?: ""

                // ----------- 3) Fetch devdetails -----------
                val devDetailsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"devdetails\"}")
                    reader.readLine()
                } ?: ""



                // ----------- Parse  -----------


                val edevsObj =  JSONObject(edevsJson)
                val edevs1 = edevsObj.getJSONArray("DEVS").getJSONObject(0)
                val edevs2 = edevsObj.getJSONArray("DEVS").getJSONObject(1)
                val edevs3 = edevsObj.getJSONArray("DEVS").getJSONObject(2)

                val poolsObj =  JSONObject(poolsJson)
                val pool1 = poolsObj.getJSONArray("POOLS").getJSONObject(0)
                val pool2 = poolsObj.getJSONArray("POOLS").getJSONObject(1)
                val pool3 = poolsObj.getJSONArray("POOLS").getJSONObject(2)

                val summaryObj = JSONObject(summaryJson)
                val summary = summaryObj.getJSONObject("Msg") // sometimes 0, check logs

                // val devDetailsObj = JSONObject(devDetailsJson)
                // val devDetails1 = devDetailsObj.getJSONArray("DEVDETAILS").getJSONObject(0)

                val ghs = summary.optString("HS RT", "0")

                val uptime = summary.optString("Elapsed", "0")

                val fanSpeedIn = summary.optString("Fan Speed In")
                val fanSpeedOut = summary.optString("Fan Speed Out")

                val estimatedFanSpeed = (
                        (fanSpeedIn.toIntOrNull() ?: 0) +
                                (fanSpeedOut.toIntOrNull() ?: 0)
                        ) / 2


                val temp1 = edevs1.optString("Temperature", "")
                val temp2 = edevs2.optString("Temperature", "")
                val temp3 = edevs3.optString("Temperature", "")



                val estimatedTemp = ((
                        (temp1.toFloatOrNull() ?: 0f) +
                                (temp2.toFloatOrNull() ?: 0f) +
                                (temp3.toFloatOrNull() ?: 0f)
                        ) / 3f).toInt()



                // ----------- Update UI -----------
                withContext(Dispatchers.Main) {
                    onHashRate(MigaHashToTeraHash(ghs.toFloatOrNull() ?: 0f))
                    onUptime(uptime.toDoubleOrNull() ?: 0.0)
                    onTemp("$estimatedTemp°C")
                    onPool1(pool1.optString("URL", ""))
                    onPool2(pool2.optString("URL", ""))
                    onPool3(pool3.optString("URL", ""))
                    onPoolUser1(pool1.optString("User", ""))
                    onPoolUser2(pool1.optString("User", ""))
                    onPoolUser3(pool1.optString("User", ""))
                    onIsPool1Alive(pool1.optString("Status", ""))
                    onIsPool2Alive(pool2.optString("Status", ""))
                    onIsPool3Alive(pool3.optString("Status", ""))
                    onFan1(estimatedFanSpeed.toString())
                    onFan2(estimatedFanSpeed.toString())
                    onFan3(estimatedFanSpeed.toString())
                    onFan4(estimatedFanSpeed.toString())
                    onDeviceName("")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError() }
                Log.e("GetAntminerData", "Error: ${e.message}", e)
            }

            delay(5000) // refresh interval
        }
    }
}
fun fetchAntminerDataLoop(
    scope: CoroutineScope,
    ipv4: String,
    port: String,
    onHashRate: (Double) -> Unit,
    onTemp: (String) -> Unit,
    onUptime: (Double) -> Unit,
    onFan1: (String) -> Unit,
    onFan2: (String) -> Unit,
    onFan3: (String) -> Unit,
    onFan4: (String) -> Unit,
    onPool1: (String) -> Unit,
    onPool2: (String) -> Unit,
    onPool3: (String) -> Unit,
    onError: () -> Unit,
    onIsPool1Alive: (String) -> Unit,
    onIsPool2Alive: (String) -> Unit,
    onIsPool3Alive: (String) -> Unit,
    onPoolUser1: (String) -> Unit,
    onPoolUser2: (String) -> Unit,
    onPoolUser3: (String) -> Unit,
    onDeviceName: (String) -> Unit

) {
    scope.launch {
        while (isActive) {
            try {
                // ----------- 1) Fetch stats -----------
                val statsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"stats\"}")
                    reader.readLine()
                } ?: ""

                // ----------- 2) Fetch pools -----------
                val poolsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                    writer.println("{\"command\":\"pools\"}")
                    reader.readLine()
                } ?: ""

                // ----------- Parse stats -----------
                val statsObj = JSONObject(statsJson)
                val stats_info = statsObj.getJSONArray("STATS").getJSONObject(0)
                val stats = statsObj.getJSONArray("STATS").getJSONObject(1) // sometimes 0, check logs

                val device_name = stats_info.optString("Type", "")
                val ghs = stats.optString("GHS 5s", "0")
                val tmp1 = stats.optString("temp2_1", "0")
                val tmp2 = stats.optString("temp2_2", "0")
                val tmp3 = stats.optString("temp2_3", "0")
                val uptime = stats.optString("Elapsed", "0")
                val avgTemp = (tmp1.toInt() + tmp2.toInt() + tmp3.toInt()) / 3

                // ----------- Parse pools -----------
                val poolsObj = JSONObject(poolsJson)
                val pools = poolsObj.getJSONArray("POOLS")

                val pool1 = pools.optJSONObject(0)?.optString("URL", "") ?: ""
                val pool2 = pools.optJSONObject(1)?.optString("URL", "") ?: ""
                val pool3 = pools.optJSONObject(2)?.optString("URL", "") ?: ""

                val isPool1 = pools.optJSONObject(0)?.optString("Status", "") ?: ""
                val isPool2 = pools.optJSONObject(1)?.optString("Status", "") ?: ""
                val isPool3 = pools.optJSONObject(2)?.optString("Status", "") ?: ""

                val poolUser1 = pools.optJSONObject(0)?.optString("User", "") ?: ""
                val poolUser2 = pools.optJSONObject(1)?.optString("User", "") ?: ""
                val poolUser3 = pools.optJSONObject(2)?.optString("User", "") ?: ""

                // ----------- Update UI -----------
                withContext(Dispatchers.Main) {
                    if (ghs.isEmpty() || avgTemp == 0) {
                        onError()
                    }
                    onHashRate(GigaHashToTeraHash(ghs.toFloatOrNull() ?: 0f))
                    onTemp("$avgTemp°C")
                    onUptime(uptime.toDoubleOrNull() ?: 0.0)

                    onFan1(stats.optString("fan1", "N/A"))
                    onFan2(stats.optString("fan2", "N/A"))
                    onFan3(stats.optString("fan3", "N/A"))
                    onFan4(stats.optString("fan4", "N/A"))

                    onPool1(pool1)
                    onPool2(pool2)
                    onPool3(pool3)

                    onIsPool1Alive(isPool1)
                    onIsPool2Alive(isPool2)
                    onIsPool3Alive(isPool3)

                    onPoolUser1(poolUser1)
                    onPoolUser2(poolUser2)
                    onPoolUser3(poolUser3)

                    onDeviceName(device_name)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError() }
                Log.e("GetAntminerData", "Error: ${e.message}", e)
            }

            delay(5000) // refresh interval
        }
    }
}

// One-shot fetch (runs once, no loop)
suspend fun fetchWhatsminerDataOnce(
    ipv4: String,
    port: String,
    onHashRate: (Double) -> Unit,
    onTemp: (String) -> Unit,
    onUptime: (Double) -> Unit,
    onFan1: (String) -> Unit,
    onFan2: (String) -> Unit,
    onFan3: (String) -> Unit,
    onFan4: (String) -> Unit,
    onPool1: (String) -> Unit,
    onPool2: (String) -> Unit,
    onPool3: (String) -> Unit,
    onIsPool1Alive: (String) -> Unit,
    onIsPool2Alive: (String) -> Unit,
    onIsPool3Alive: (String) -> Unit,
    onPoolUser1: (String) -> Unit,
    onPoolUser2: (String) -> Unit,
    onPoolUser3: (String) -> Unit,
    onDeviceName: (String) -> Unit,

    onError: () -> Unit
) {
    try {
        // -------- Stats --------
        val statsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"stats\"}")
            reader.readLine()
        } ?: return

        // -------- Pools --------
        val poolsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"pools\"}")
            reader.readLine()
        } ?: return

        val stats_info =JSONObject(statsJson).getJSONArray("STATS").getJSONObject(0)
        val statsObj = JSONObject(statsJson).getJSONArray("STATS").getJSONObject(1)
        val pools = JSONObject(poolsJson).getJSONArray("POOLS")

        val ghs = statsObj.optString("GHS 5s", "0")
        val uptime = statsObj.optString("Elapsed", "0")
        val avgTemp = listOf(
            statsObj.optString("temp2_1", "0"),
            statsObj.optString("temp2_2", "0"),
            statsObj.optString("temp2_3", "0"),
        ).map { it.toIntOrNull() ?: 0 }.average().toInt()

        withContext(Dispatchers.Main) {
            if (ghs.isEmpty() || avgTemp == 0) {
                onError()
            }

            Log.d("restt", ghs)
            onHashRate(GigaHashToTeraHash(ghs.toFloatOrNull() ?: 0f))
            onTemp("$avgTemp°C")
            onUptime(uptime.toDoubleOrNull() ?: 0.0)

            onFan1(statsObj.optString("fan1", "N/A"))
            onFan2(statsObj.optString("fan2", "N/A"))
            onFan3(statsObj.optString("fan3", "N/A"))
            onFan4(statsObj.optString("fan4", "N/A"))

            onPool1(pools.optJSONObject(0)?.optString("URL", "") ?: "")
            onPool2(pools.optJSONObject(1)?.optString("URL", "") ?: "")
            onPool3(pools.optJSONObject(2)?.optString("URL", "") ?: "")

            onIsPool1Alive(pools.optJSONObject(0)?.optString("Status", "") ?: "")
            onIsPool2Alive(pools.optJSONObject(1)?.optString("Status", "") ?: "")
            onIsPool3Alive(pools.optJSONObject(2)?.optString("Status", "") ?: "")

            onPoolUser1(pools.optJSONObject(0)?.optString("User", "") ?: "")
            onPoolUser2(pools.optJSONObject(1)?.optString("User", "") ?: "")
            onPoolUser3(pools.optJSONObject(2)?.optString("User", "") ?: "")
            onDeviceName(stats_info.optString("Type", "") ?: "")

        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) { onError() }
        Log.e("Antminer", "Error: ${e.message}", e)
    }
}

suspend fun fetchAntminerDataOnce(
    ipv4: String,
    port: String,
    onHashRate: (Double) -> Unit,
    onTemp: (String) -> Unit,
    onUptime: (Double) -> Unit,
    onFan1: (String) -> Unit,
    onFan2: (String) -> Unit,
    onFan3: (String) -> Unit,
    onFan4: (String) -> Unit,
    onPool1: (String) -> Unit,
    onPool2: (String) -> Unit,
    onPool3: (String) -> Unit,
    onIsPool1Alive: (String) -> Unit,
    onIsPool2Alive: (String) -> Unit,
    onIsPool3Alive: (String) -> Unit,
    onPoolUser1: (String) -> Unit,
    onPoolUser2: (String) -> Unit,
    onPoolUser3: (String) -> Unit,
    onDeviceName: (String) -> Unit,

    onError: () -> Unit
) {
    try {
        // ----------- 1) Fetch stats -----------
        val edevsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"edevs\"}")
            reader.readLine()
        } ?: ""

        // ----------- 2) Fetch pools -----------
        val poolsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"pools\"}")
            reader.readLine()
        } ?: ""

        // ----------- 3) Fetch summary -----------
        val summaryJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"summary\"}")
            reader.readLine()
        } ?: ""

        // ----------- 3) Fetch devdetails -----------
        val devDetailsJson = Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer.println("{\"command\":\"devdetails\"}")
            reader.readLine()
        } ?: ""



        // ----------- Parse  -----------


        val edevsObj =  JSONObject(edevsJson)
        val edevs1 = edevsObj.getJSONArray("DEVS").getJSONObject(0)
        val edevs2 = edevsObj.getJSONArray("DEVS").getJSONObject(1)
        val edevs3 = edevsObj.getJSONArray("DEVS").getJSONObject(2)

        val poolsObj =  JSONObject(poolsJson)
        val pool1 = poolsObj.getJSONArray("POOLS").getJSONObject(0)
        val pool2 = poolsObj.getJSONArray("POOLS").getJSONObject(1)
        val pool3 = poolsObj.getJSONArray("POOLS").getJSONObject(2)

        val summaryObj = JSONObject(summaryJson)
        val summary = summaryObj.getJSONObject("Msg") // sometimes 0, check logs

        // val devDetailsObj = JSONObject(devDetailsJson)
       // val devDetails1 = devDetailsObj.getJSONArray("DEVDETAILS").getJSONObject(0)

        val ghs = summary.optString("HS RT", "0")

        val uptime = summary.optString("Elapsed", "0")

        val fanSpeedIn = summary.optString("Fan Speed In")
        val fanSpeedOut = summary.optString("Fan Speed Out")

        val estimatedFanSpeed = (
                (fanSpeedIn.toIntOrNull() ?: 0) +
                        (fanSpeedOut.toIntOrNull() ?: 0)
                ) / 2


        val temp1 = edevs1.optString("Temperature", "")
        val temp2 = edevs2.optString("Temperature", "")
        val temp3 = edevs3.optString("Temperature", "")



        val estimatedTemp = (
                (temp1.toFloatOrNull() ?: 0f) +
                        (temp2.toFloatOrNull() ?: 0f) +
                        (temp3.toFloatOrNull() ?: 0f)
                ) / 3f



        // ----------- Update UI -----------
        withContext(Dispatchers.Main) {
            onHashRate(MigaHashToTeraHash(ghs.toFloatOrNull() ?: 0f))
            onUptime(uptime.toDoubleOrNull() ?: 0.0)
            onTemp(estimatedTemp.toString())
            onPool1(pool1.optString("URL", ""))
            onPool2(pool2.optString("URL", ""))
            onPool3(pool3.optString("URL", ""))
            onPoolUser1(pool1.optString("User", ""))
            onPoolUser2(pool1.optString("User", ""))
            onPoolUser3(pool1.optString("User", ""))
            onIsPool1Alive(pool1.optString("Status", ""))
            onIsPool2Alive(pool2.optString("Status", ""))
            onIsPool3Alive(pool3.optString("Status", ""))
            onFan1(estimatedFanSpeed.toString())
            onFan2(estimatedFanSpeed.toString())
            onFan3(estimatedFanSpeed.toString())
            onFan4(estimatedFanSpeed.toString())
            onDeviceName("")
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) { onError() }
        Log.e("GetAntminerData", "Error: ${e.message}", e)
    }
}
