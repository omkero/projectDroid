package com.example.projectdroid.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projectdroid.components.BackButtonWithTooltip
import com.example.projectdroid.components.SetStatusBarContentColor
import com.example.projectdroid.ui.theme.AppHorizontalPadding
import com.example.projectdroid.ui.theme.PrimaryBackground
import com.example.projectdroid.ui.theme.PrimaryFontSize
import com.example.projectdroid.ui.theme.SecondaryBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinerDetailsScreen(context: Context, navController: NavHostController) {
    SetStatusBarContentColor(false)

    var currentTab = remember { mutableIntStateOf(0) }
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
            Column (
                Modifier
                    .padding(innerPadding)
                    .background(PrimaryBackground)
            ) {
                Column (
                    modifier = Modifier.padding(AppHorizontalPadding)
                ) {
                    Text("Miner Details", color = Color.White, fontSize = PrimaryFontSize)
                }
            }
        }
    }
}