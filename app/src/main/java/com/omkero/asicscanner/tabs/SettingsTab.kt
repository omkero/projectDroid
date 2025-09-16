package com.omkero.asicscanner.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.omkero.asicscanner.ui.theme.AppHorizontalPadding
import com.omkero.asicscanner.ui.theme.AppRoundedDp
import com.omkero.asicscanner.ui.theme.PrimaryBackground
import com.omkero.asicscanner.ui.theme.PrimaryFontSize
import com.omkero.asicscanner.ui.theme.SecondaryBackground

@Composable
fun SettingsTab(innerPadding: PaddingValues, navController: NavHostController) {
    Column (
        modifier = Modifier.padding(innerPadding)
            .background(PrimaryBackground)
            .fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.padding(AppHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Info", color = Color.White, fontSize = PrimaryFontSize)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppRoundedDp))
                    .background(SecondaryBackground)
                    .padding(14.dp)
                ,

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("By Omarker ", color = Color.LightGray)
                Text("v0.0.1", color = Color.LightGray)
            }
        }
    }
}