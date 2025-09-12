package com.example.projectdroid.tabs

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun DashBoardTab(innerPadding: PaddingValues, navController: NavHostController) {
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
        }
    }
}