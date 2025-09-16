package com.omkero.asicscanner.tabs

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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.omkero.asicscanner.components.DisplayCard
import com.omkero.asicscanner.components.DisplayMinerStatus
import com.omkero.asicscanner.components.DisplayTotalHashRateCard
import com.omkero.asicscanner.repo.loadMiners
import com.omkero.asicscanner.repo.removeMinerAt
import com.omkero.asicscanner.ui.theme.AppHorizontalPadding
import com.omkero.asicscanner.ui.theme.PrimaryBackground
import com.omkero.asicscanner.ui.theme.PrimaryFontSize
import com.omkero.asicscanner.ui.theme.SecondaryBackground
import com.omkero.asicscanner.viewmodel.MinerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "DefaultLocale")
@Composable
fun DashBoardTab(
    innerPadding: PaddingValues,
    navController: NavHostController,
    context: Context,
    minerViewModel: MinerViewModel
) {

    var miners = minerViewModel.miners.collectAsState()
    var isItemRemoveDialog by remember { mutableStateOf(false) }
    var targetMiner by remember { mutableIntStateOf(0) }
    val minersMap = remember { mutableStateMapOf<String, Double>() }
    var tempUniqueKey by remember { mutableStateOf("") }


    // realtime
    val scope = CoroutineScope(Dispatchers.IO)

    LaunchedEffect(UInt) {
        minerViewModel.getMiners(context)
    }

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .background(PrimaryBackground)
            .fillMaxSize()
        ,
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
                                    isItemRemoveDialog = false
                                    minersMap.remove(tempUniqueKey)
                                    tempUniqueKey = ""
                                    minerViewModel.getMiners(context)

                                },
                                contentPadding = PaddingValues(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red, // Purple as an example
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
        if (miners.value.isEmpty()) {
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
                    DisplayCard(
                        "Total Miners",
                        "${miners.value.size}",
                        Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Miner Status", color = Color.White, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(12.dp))

            }
        } else {
            Column (
                modifier = Modifier.padding(AppHorizontalPadding)
                ,

            ) {
                Text("Overview", color = Color.White, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DisplayTotalHashRateCard(
                        "Total Hashrate",
                        Modifier.weight(1f),
                        minersMap
                    )
                    DisplayCard(
                        "Total Miners",
                        "${miners.value.size}",
                        Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Miner Status", color = Color.White, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn (
                    modifier = Modifier.fillMaxWidth()
                    ,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(miners.value) { index,item ->

                        DisplayMinerStatus(
                            item.name,
                            item.ipv4,
                            item.port,
                            item.type,
                            item.uniqueKey,
                            onClick = {
                                navController.navigate("MinerDetailsScreen/${index}")
                            },
                            onLongPress = { unique ->
                                targetMiner = index
                                isItemRemoveDialog = true
                                tempUniqueKey = unique
                            },
                            onTotalHashRate = { hashRate, ipv4 ->

                            },
                            minersMap
                        )
                    }
                }
            }
        }
    }
}

