package com.example.projectdroid.components

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectdroid.ui.theme.DarkRed
import com.example.projectdroid.ui.theme.PrimaryFontSize
import com.example.projectdroid.ui.theme.SecondaryBackground
import com.example.projectdroid.ui.theme.SecondaryFontSize
import com.example.projectdroid.ui.theme.ServerIcon
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.projectdroid.ui.theme.AppHorizontalPadding
import com.example.projectdroid.ui.theme.AppRoundedDp
import com.example.projectdroid.utils.GigaHashToTeraHash
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

@Composable
fun DisplayCard(
    topText: String,
    bottomText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(AppRoundedDp))
            .background(SecondaryBackground)
            .padding(vertical = 10.dp, horizontal = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(topText, color = Color.LightGray, fontSize = SecondaryFontSize)
            Spacer(modifier = Modifier.height(6.dp))
            Text(bottomText, color = Color.White, fontSize = PrimaryFontSize)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "DefaultLocale")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayMinerStatus(
    Title: String,
    ipv4: String,
    port: String,
    onClick: () -> Any,
    onLongPress: () -> Any,
    onTotalHashRate: (Double) -> Unit
) {
    val haptics = LocalHapticFeedback.current
    var isMinerError by remember { mutableStateOf(false) }
    var hash by remember { mutableDoubleStateOf(0.0) }
    var temp by remember { mutableStateOf("- -") }
    val scope = CoroutineScope(Dispatchers.IO)
    val max_url_length = 30

    // ✅ Runs once, cancels automatically when Composable leaves
    LaunchedEffect(Unit) {
        scope.launch {
            while (isActive) {
                try {
                    Socket(ipv4, port.toIntOrNull() ?: 0).use { socket ->
                        val writer = PrintWriter(socket.getOutputStream(), true)
                        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                        writer.println("{\"command\":\"stats\"}")
                        val rawResponse = reader.readLine() ?: return@use

                        val json = JSONObject(rawResponse)
                        val stats1 = json.getJSONArray("STATS").getJSONObject(1)
                        val ghs = stats1.getString("GHS 5s")

                        val tmp1 = stats1.getString("temp2_1")
                        val tmp2 = stats1.getString("temp2_2")
                        val tmp3 = stats1.getString("temp2_3")

                        val res = (tmp1.toInt() + tmp2.toInt() + tmp3.toInt()) / 3

                        if (ghs.isEmpty()) {
                            isMinerError = false
                        }

                        withContext(Dispatchers.Main) {
                            hash = GigaHashToTeraHash(ghs.toFloatOrNull() ?: 0.0f)
                            temp = "$res°C"
                            onTotalHashRate(GigaHashToTeraHash(ghs.toFloatOrNull() ?: 0.0f))
                        }
                    }
                } catch (e: Exception) {
                    isMinerError = true
                    Log.d("DisplayMinerStatus", "Error: ${e.message}")
                    e.printStackTrace()

                }
                delay(5000)
            }
        }
    }
    Box (
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(AppRoundedDp))
            .background(SecondaryBackground)
            .clickable{

            }
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    // Handle long-press event
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPress()
                    // Perform your desired action on long press
                },
            )
            .padding(vertical = 10.dp, horizontal = 15.dp)
        ,
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Icon(
                        ServerIcon,
                        "ServerIcon",
                        modifier = Modifier.size(20.dp)
                        ,
                        tint = Color.White
                    )
                }
                Column {
                    Text(Title, color = Color.LightGray, fontSize = PrimaryFontSize)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(if (isMinerError || hash == 0.0 || temp.isEmpty()) "Offline" else "Online",
                        color = if (isMinerError || hash == 0.0 || temp.isEmpty()) Color.Red else Color.Green,
                            fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(if (!isMinerError || hash > 0.0 || temp.isNotEmpty()) Color.Green.copy(alpha = 0.1f) else DarkRed.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Column (
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(if (isMinerError || hash == 0.0) "- -" else "${String.format("%.2f", hash)} TH/s", color = Color.LightGray, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(6.dp))
                Text(if (isMinerError || temp.isEmpty()) "- -" else temp, color = Color.White, fontSize = SecondaryFontSize)
            }
        }
    }
}

@Composable
fun DisplayPool(title: String, url: String, isPoolAlive: String, poolUser: String) {
    Box (
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(AppRoundedDp))
            .background(SecondaryBackground)
            .padding(vertical = 10.dp, horizontal = 15.dp)
        ,
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(title, color = Color.White, fontSize = PrimaryFontSize, maxLines = 1)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(if (url.length >= 40) url.substring(0, 40) + " .." else url,
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20)),
                        maxLines = 1
                    )
                }
            }
            Column (
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    if (poolUser.length >= 10) poolUser.substring(0, 10) + " .." else poolUser ,
                    color = Color.LightGray,
                    fontSize = SecondaryFontSize,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(if (isPoolAlive == "Alive") "Active" else "Detective",
                    color = if (isPoolAlive == "Alive") Color.Green else Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .background(if (isPoolAlive == "Alive") Color.Green.copy(alpha = 0.1f) else DarkRed.copy(alpha = 0.1f))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    maxLines = 1
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonWithTooltip(navController: NavController, onClick: () -> Unit) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip (
                containerColor = Color.Black,

                        modifier = Modifier.padding(vertical = 15.dp, horizontal = AppHorizontalPadding)
            ) {
                Text("Go Back", color = Color.White)
            }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "go_back")
        }
    }
}

// just call this function on top of your composable and set if darkIcons true or false
@Composable
fun SetStatusBarContentColor(
    useDarkIcons: Boolean,
) {
    val view = LocalView.current
    val activity = view.context as Activity
    val window = activity.window

    DisposableEffect(Unit) {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)


        // Set icon color
        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = useDarkIcons
        insetsController.isAppearanceLightNavigationBars = useDarkIcons

        onDispose {}
    }
}
