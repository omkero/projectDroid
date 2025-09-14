package com.omkero.asicscanner.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.omkero.asicscanner.ui.theme.AppHorizontalPadding
import com.omkero.asicscanner.ui.theme.PrimaryBackground
import com.omkero.asicscanner.ui.theme.PrimaryFontSize

@Composable
fun SettingsTab(innerPadding: PaddingValues, navController: NavHostController) {
    Column (
        modifier = Modifier.padding(innerPadding)
            .background(PrimaryBackground)
            .fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.padding(AppHorizontalPadding)
        ) {
            Text("Settings", color = Color.White, fontSize = PrimaryFontSize)
        }
    }
}