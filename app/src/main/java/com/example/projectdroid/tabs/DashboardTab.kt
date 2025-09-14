package com.example.projectdroid.tabs

import MinerType
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.projectdroid.components.DisplayCard
import com.example.projectdroid.components.DisplayMinerStatus
import com.example.projectdroid.repo.loadMiners
import com.example.projectdroid.repo.removeMinerAt
import com.example.projectdroid.ui.theme.AppHorizontalPadding
import com.example.projectdroid.ui.theme.DarkRed
import com.example.projectdroid.ui.theme.PrimaryBackground
import com.example.projectdroid.ui.theme.PrimaryFontSize
import com.example.projectdroid.ui.theme.SecondaryBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "DefaultLocale")
@Composable
fun DashBoardTab(innerPadding: PaddingValues, navController: NavHostController, context: Context) {
    var response: String? by remember { mutableStateOf("") }
    var isConnected by remember { mutableStateOf(false) }
    var miners by remember { mutableStateOf<List<MinerType>>(loadMiners(context)) }
    var isItemRemoveDialog by remember { mutableStateOf(false) }
    var targetMiner by remember { mutableIntStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    var totalHashRate by remember { mutableDoubleStateOf(0.0) }

    // realtime
    val scope = CoroutineScope(Dispatchers.IO)
    /*
    *     scope.launch {
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
                response     = "Ops Something Went Wrong !!"
                e.printStackTrace()
            }

            delay(5000)
        }
    }
    *
    * */

    LaunchedEffect(UInt) {
        miners = loadMiners(context)
    }

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .background(PrimaryBackground)
            .fillMaxSize(),
    ) {
        if (isItemRemoveDialog) {
            BasicAlertDialog(
                onDismissRequest = {
                    isItemRemoveDialog = false
                },
                properties = DialogProperties(), content = {
                    Card (
                        modifier = Modifier.size(200.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = SecondaryBackground
                        ),
                        elevation = CardDefaults.cardElevation(2.dp),

                        ) {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Are You Sure Want To remove ?", color = Color.White)
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                ,
                                shape = RoundedCornerShape(7.dp), // <-- Make it not rounded
                                onClick = {
                                    isItemRemoveDialog = false
                                },
                                border = BorderStroke(1.dp, Color.LightGray), // only width + color                            contentPadding = PaddingValues(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SecondaryBackground, // Purple as an example
                                    contentColor = Color.White,


                                    ),

                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 10.dp
                                )
                            ) {
                                Text("Cancel", fontWeight = FontWeight.Bold)
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                ,
                                shape = RoundedCornerShape(7.dp), // <-- Make it not rounded
                                onClick = {
                                    // your action
                                    removeMinerAt(context, targetMiner)
                                    miners = loadMiners(context)
                                    isItemRemoveDialog = false
                                },
                                contentPadding = PaddingValues(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkRed, // Purple as an example
                                    contentColor = Color.White,

                                    ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 10.dp
                                )
                            ) {
                                Text("Remove Miner", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                })
        }
        if (miners.isEmpty()) {
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
                    DisplayCard("Total Hashrate", "- -", Modifier.weight(1f))
                    DisplayCard("Total Miners", "${miners.size}", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Miner Status", color = Color.White, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(12.dp))

            }
        } else {
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
                    DisplayCard("Total Hashrate", "${String.format("%.2f", totalHashRate)}  TH/s", Modifier.weight(1f))
                    DisplayCard("Total Miners", "${miners.size}" , Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Miner Status", color = Color.White, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(miners) { index,item ->

                        DisplayMinerStatus(
                            item.name,
                            item.ipv4,
                            item.port,
                            onClick = {
                                navController.navigate("MinerDetailsScreen/${index}")
                            },
                            onLongPress = {
                                targetMiner = index
                                isItemRemoveDialog = true
                            },
                            onTotalHashRate = {
                                totalHashRate = it
                            }
                        )
                    }
                }

                // Display parsed miner data
                response?.let { Text(it, color = Color.White) }
            }
        }
    }
}

/*
*             Button(onClick = {
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
*  */