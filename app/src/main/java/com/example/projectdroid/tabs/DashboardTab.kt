package com.example.projectdroid.tabs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projectdroid.components.DisplayCard
import com.example.projectdroid.components.DisplayMinerStatus
import com.example.projectdroid.ui.theme.AppHorizontalPadding
import com.example.projectdroid.ui.theme.PrimaryBackground
import com.example.projectdroid.ui.theme.PrimaryFontSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.json.JSONObject

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DashBoardTab(innerPadding: PaddingValues, navController: NavHostController) {
    var response: String? by remember { mutableStateOf("") }
    var isConnected by remember { mutableStateOf(false) }


    // realtime
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        while (isActive) {
            try {
                Socket("192.168.1.172", 4028).use { socket ->
                    val writer = PrintWriter(socket.getOutputStream(), true)
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                    writer.println("{\"command\":\"stats\"}")
                    val rawResponse = reader.readLine() ?: return@use

                    val json = JSONObject(rawResponse)
                    val stats1 = json.getJSONArray("STATS").getJSONObject(1)
                    val ghs = stats1.getString("GHS 5s")

                    withContext(Dispatchers.Main) {
                        response = ghs
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            delay(5000)
        }
    }

    Column (
        modifier = Modifier.padding(innerPadding)
            .background(PrimaryBackground)
            .fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.padding(AppHorizontalPadding)
        ) {
            Text("Overview", color = Color.White, fontSize = PrimaryFontSize)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DisplayCard("Total Hashrate", "173 TH/s", Modifier.weight(1f))
                DisplayCard("Total Miners", "5", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Miner Status", color = Color.White, fontSize = PrimaryFontSize)
            Spacer(modifier = Modifier.height(12.dp))
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                DisplayMinerStatus("Miner 1",
                    "Online",
                    "90 TH/s",
                    "75°C",
                    onClick = {
                        navController.navigate("MinerDetailsScreen")
                    }
                )
                DisplayMinerStatus("Miner 2",
                    "Offline",
                    "86 TH/s",
                    "68°C",
                    onClick = {
                        navController.navigate("MinerDetailsScreen")
                    }
                )
            }


            // Display parsed miner data
            response?.let { Text(it, color = Color.White) }

            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    try {
                        val socket = Socket("192.168.1.172", 4028)
                        val writer = PrintWriter(socket.getOutputStream(), true)
                        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                        // Send command
                        writer.println("{\"command\":\"stats\"}")

                        // Read response
                        val rawResponse = reader.readLine()
                        // Parse with org.json (lenient & simple)
                        val json = JSONObject(rawResponse)

                        val status = json.getJSONArray("STATUS").getJSONObject(0)
                        val stats0 = json.getJSONArray("STATS").getJSONObject(0)
                        val stats1 = json.getJSONArray("STATS").getJSONObject(1)

                        response = stats1.getString("GHS 5s")
                        socket.close()
                    } catch (e: Exception) {
                        Log.d("err", "$e.message")
                        response = "Connection error: ${e.message}"
                        isConnected = false
                    }
                }
            }) {
                Text("Connect to Miner")
            }
        }
    }
}