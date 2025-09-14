package com.omkero.asicscanner.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.omkero.asicscanner.components.SetStatusBarContentColor
import com.omkero.asicscanner.tabs.DashBoardTab
import com.omkero.asicscanner.tabs.SettingsTab
import com.omkero.asicscanner.ui.theme.Cpu
import com.omkero.asicscanner.ui.theme.LightGreeen
import com.omkero.asicscanner.ui.theme.PrimaryBackground
import com.omkero.asicscanner.ui.theme.SecondaryBackground


data class TabItem (
    val Icon: ImageVector,
    val Title: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context, navController: NavHostController) {
    SetStatusBarContentColor(false)
    var currentTab = remember { mutableIntStateOf(0) }
    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Icon(Cpu, "", modifier = Modifier.size(25.dp))
                        Text("Miner Scanner")
                    }

                },
                colors = TopAppBarColors(
                    containerColor = SecondaryBackground,
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color.Blue,
                    navigationIconContentColor = Color.Blue,
                    actionIconContentColor = Color.Blue
                )
            )
        },
        bottomBar = {
            BottomBar(currentTab)
        },
        floatingActionButton = {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip (
                        modifier = Modifier.padding(vertical = 15.dp),
                        containerColor = Color.Black
                    ) {
                        Text("Add Miner", color = Color.White)
                    }
                },
                state = rememberTooltipState(),
            ) {
                FloatingActionButton(
                    containerColor = LightGreeen,
                    contentColor = Color.Black,
                    onClick = {
                        navController.navigate("AddMinerScreen")
                    },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }

        },
        floatingActionButtonPosition = FabPosition.End


    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackground) // your fade background color
        ) {
            Crossfade(
                targetState = currentTab.intValue,
                animationSpec = tween(300)
            ) { tab ->
                when (tab) {
                    0 -> DashBoardTab(innerPadding, navController, context)
                    1 -> SettingsTab(innerPadding, navController)
                }
            }
        }
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun BottomBar(currentTab: MutableIntState) {
    val Tabs = listOf<TabItem>(
        TabItem(Icons.Filled.Home, "Dashboard"),
        TabItem(Icons.Default.Settings, "Settings"),
    )

    NavigationBar(
        tonalElevation = 10.dp,
        containerColor = SecondaryBackground,

        ) {
        Tabs.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentTab.intValue == index,
                onClick = {
                    currentTab.intValue = index
                },
                icon = {

                    Icon(item.Icon, contentDescription = item.Title, modifier = Modifier.size(19.dp))
                       },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = PrimaryBackground
                ),
                label = { Text(item.Title) }
            )
        }
    }
}