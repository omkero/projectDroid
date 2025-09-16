package com.omkero.asicscanner.screens

import MinerType
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.omkero.asicscanner.components.BackButtonWithTooltip
import com.omkero.asicscanner.components.DisplayCard
import com.omkero.asicscanner.components.DisplayPool
import com.omkero.asicscanner.components.SetStatusBarContentColor
import com.omkero.asicscanner.repo.fetchAntminerDataLoop
import com.omkero.asicscanner.repo.fetchAntminerDataOnce
import com.omkero.asicscanner.repo.fetchWhatsminerDataLoop
import com.omkero.asicscanner.repo.fetchWhatsminerDataOnce
import com.omkero.asicscanner.repo.loadMiners
import com.omkero.asicscanner.ui.theme.AppHorizontalPadding
import com.omkero.asicscanner.ui.theme.PrimaryBackground
import com.omkero.asicscanner.ui.theme.PrimaryFontSize
import com.omkero.asicscanner.ui.theme.SecondaryBackground
import com.omkero.asicscanner.ui.theme.SecondaryFontSize
import com.omkero.asicscanner.utils.UptimeToDate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinerDetailsScreen(context: Context, navController: NavHostController, minerIndex: Int) {
    SetStatusBarContentColor(false)
    val scrollState = rememberScrollState()
    var miners by remember { mutableStateOf<List<MinerType>>(loadMiners(context)) }
    var ipv4 = miners[minerIndex].ipv4
    var port = miners[minerIndex].port
    var name = miners[minerIndex].name
    var minerType = miners[minerIndex].type

    val scope = CoroutineScope(Dispatchers.IO)

    var deviceName by remember { mutableStateOf("") }

    var isMinerError by remember { mutableStateOf(false) }
    var hashRate by remember { mutableDoubleStateOf(0.0) }
    var temp by remember { mutableStateOf("") }
    var uptimeUnix by remember { mutableDoubleStateOf(0.0) }
    var isRefreshing by remember { mutableStateOf(false) }
    var fan1Speed by remember { mutableStateOf("") }
    var fan2Speed by remember { mutableStateOf("0") }
    var fan3Speed by remember { mutableStateOf("") }
    var fan4Speed by remember { mutableStateOf("") }

    var poolUrl1 by remember { mutableStateOf("") }
    var isPool1Alive by remember { mutableStateOf("") }
    var poolUser1 by remember { mutableStateOf("") }

    var poolUrl2 by remember { mutableStateOf("") }
    var isPool2Alive by remember { mutableStateOf("") }
    var poolUser2 by remember { mutableStateOf("") }

    var poolUrl3 by remember { mutableStateOf("") }
    var isPool3Alive by remember { mutableStateOf("") }
    var poolUser3 by remember { mutableStateOf("") }



    // ✅ Runs once, cancels automatically when Composable leaves
    LaunchedEffect(Unit) {
        if (minerType == "Antminer") {
            fetchAntminerDataLoop(
                scope,
                ipv4,
                port,
                onHashRate = { hashRate = it },
                onTemp = { temp = it },
                onUptime = { uptimeUnix = it },
                onFan1 = { fan1Speed = it },
                onFan2 = { fan2Speed = it },
                onFan3 = { fan3Speed = it },
                onFan4 = { fan4Speed = it },
                onPool1 = { poolUrl1 = it },
                onPool2 = { poolUrl2 = it },
                onPool3 = { poolUrl3 = it },
                onError = { isMinerError = true },
                onIsPool1Alive = { isPool1Alive = it },
                onIsPool2Alive = { isPool2Alive = it },
                onIsPool3Alive = { isPool3Alive = it },
                onPoolUser1 = {poolUser1 = it},
                onPoolUser2 = {poolUser2 = it},
                onPoolUser3 = {poolUser3 = it},
                onDeviceName = { deviceName = it },
                onHashRateSet = {hashRate, ipv4 ->

                }

            )
        } else if (minerType == "Whatsminer") {
            fetchWhatsminerDataLoop(
                scope,
                ipv4,
                port,
                onHashRate = { hashRate = it },
                onTemp = { temp = it },
                onUptime = { uptimeUnix = it },
                onFan1 = { fan1Speed = it },
                onFan2 = { fan2Speed = it },
                onFan3 = { fan3Speed = it },
                onFan4 = { fan4Speed = it },
                onPool1 = { poolUrl1 = it },
                onPool2 = { poolUrl2 = it },
                onPool3 = { poolUrl3 = it },
                onError = { isMinerError = true },
                onIsPool1Alive = { isPool1Alive = it },
                onIsPool2Alive = { isPool2Alive = it },
                onIsPool3Alive = { isPool3Alive = it },
                onPoolUser1 = {poolUser1 = it},
                onPoolUser2 = {poolUser2 = it},
                onPoolUser3 = {poolUser3 = it},
                onDeviceName = { deviceName = it },
                onHashRateSet = {hashRate, ipv4 ->

                }
            )
        }
    }


    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Miner Details", fontSize = PrimaryFontSize)

                },
                navigationIcon = {
                    BackButtonWithTooltip(
                        navController,
                        onClick = {
                            navController.popBackStack()
                        }
                    )

                },
                colors = TopAppBarColors(
                    containerColor = SecondaryBackground,
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.Blue
                )
            )
        }


    ) { innerPadding ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackground)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        isMinerError = false
                        try {
                            if (minerType == "Antminer") {
                                fetchAntminerDataOnce(
                                    ipv4,
                                    port,
                                    onHashRate = { hashRate = it },
                                    onTemp = { temp = it },
                                    onUptime = { uptimeUnix = it },
                                    onFan1 = { fan1Speed = it },
                                    onFan2 = { fan2Speed = it },
                                    onFan3 = { fan3Speed = it },
                                    onFan4 = { fan4Speed = it },
                                    onPool1 = { poolUrl1 = it },
                                    onPool2 = { poolUrl2 = it },
                                    onPool3 = { poolUrl3 = it },
                                    onError = { isMinerError = false },
                                    onIsPool1Alive = { isPool1Alive = it },
                                    onIsPool2Alive = { isPool2Alive = it },
                                    onIsPool3Alive = { isPool3Alive = it },
                                    onPoolUser1 = { poolUser1 = it },
                                    onPoolUser2 = { poolUser2 = it },
                                    onPoolUser3 = { poolUser3 = it },
                                    onDeviceName = { deviceName = it },
                                    onHashRateSet = {hashRate, ipv4 ->

                                    }
                                )
                            } else if (minerType == "Whatsminer") {
                                fetchWhatsminerDataOnce(
                                    ipv4,
                                    port,
                                    onHashRate = { hashRate = it },
                                    onTemp = { temp = it },
                                    onUptime = { uptimeUnix = it },
                                    onFan1 = { fan1Speed = it },
                                    onFan2 = { fan2Speed = it },
                                    onFan3 = { fan3Speed = it },
                                    onFan4 = { fan4Speed = it },
                                    onPool1 = { poolUrl1 = it },
                                    onPool2 = { poolUrl2 = it },
                                    onPool3 = { poolUrl3 = it },
                                    onError = { isMinerError = false },
                                    onIsPool1Alive = { isPool1Alive = it },
                                    onIsPool2Alive = { isPool2Alive = it },
                                    onIsPool3Alive = { isPool3Alive = it },
                                    onPoolUser1 = { poolUser1 = it },
                                    onPoolUser2 = { poolUser2 = it },
                                    onPoolUser3 = { poolUser3 = it },
                                    onDeviceName = { deviceName = it },
                                    onHashRateSet = {hashRate, ipv4 ->

                                    }
                                )
                            }
                        } finally {
                            isRefreshing = false
                        }
                    }
                }
            ) {
                Column (
                    Modifier
                        .padding(innerPadding)
                        .background(PrimaryBackground)
                        .verticalScroll(scrollState) // Apply the verticalScroll modifier
                ) {
                    Column (
                        modifier = Modifier.padding(AppHorizontalPadding)
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(if (name.length > 10) "${name.substring(0, 10).toUpperCase()} ..  Monitoring" else "${name.toUpperCase()}  Monitoring", color = Color.White, fontSize = PrimaryFontSize)
                                if (deviceName.isNotEmpty()) {
                                    Text(if (isMinerError || hashRate == 0.0) "- -" else deviceName, color = Color.LightGray, fontSize = SecondaryFontSize)
                                }
                            }
                            Text(minerType, color = Color.LightGray, fontSize = SecondaryFontSize)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row  (
                            modifier = Modifier.fillMaxWidth(),

                            ) {
                            DisplayCard(
                                "Host Name | Port",
                                if (isMinerError || hashRate == 0.0) "- - " else if (miners[minerIndex].ipv4.length > 20 ) miners[minerIndex].ipv4.substring(0, 20)  + ":" + miners[minerIndex].port else miners[minerIndex].ipv4 + ":" + miners[minerIndex].port ,
                                Modifier.weight(1f),
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DisplayCard(
                                "Current Hashrate",
                                if (isMinerError || hashRate == 0.0) "- - " else "${String.format("%.2f", hashRate)}  TH/s",
                                Modifier.weight(1f),
                            )
                            DisplayCard(
                                "Temperature",
                                if (isMinerError || temp.isEmpty()) "- -" else temp,
                                Modifier.weight(1f),
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DisplayCard(
                                "Device Uptime",
                                if (isMinerError || hashRate == 0.0 || temp.isEmpty()) "- - " else UptimeToDate(uptimeUnix),
                                Modifier.weight(1f),
                            )
                            DisplayCard(
                                "Status",
                                if (isMinerError || hashRate == 0.0 || temp.isEmpty()) "Offline" else "Online",
                                Modifier.weight(1f),
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Fan Speed (r/min)", color = Color.White, fontSize = PrimaryFontSize)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DisplayCard(
                                if (miners[minerIndex].type == "Antminer") "Fan 1" else "Fan In",
                                if (isMinerError || fan1Speed.isEmpty()) "- - " else fan1Speed,
                                Modifier.weight(1f),

                            )
                            DisplayCard(
                                if (miners[minerIndex].type == "Antminer") "Fan 1" else "Fan Out",
                                if (isMinerError || fan2Speed.isEmpty()) "- - " else fan2Speed,
                                Modifier.weight(1f),

                            )
                        }
                        if (miners[minerIndex].type == "Antminer") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DisplayCard(
                                    "Fan 3",
                                    if (isMinerError || fan3Speed.isEmpty()) "- - " else fan3Speed,
                                    Modifier.weight(1f),

                                    )
                                DisplayCard(
                                    "Fan 4",
                                    if (isMinerError || fan4Speed.isEmpty()) "- - " else fan4Speed,
                                    Modifier.weight(1f),

                                    )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Pools", color = Color.White, fontSize = PrimaryFontSize)
                        Spacer(modifier = Modifier.height(12.dp))
                        DisplayPool("Pool 1",
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUrl1,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else isPool1Alive,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUser1
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        DisplayPool(
                            "Pool 2",
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUrl2,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else isPool2Alive,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUser2
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        DisplayPool(
                            "Pool 3",
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUrl3,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else isPool3Alive,
                            if (isMinerError || fan1Speed.isEmpty()) "- - " else poolUser3
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                    }
                }
            }

        }
    }
}