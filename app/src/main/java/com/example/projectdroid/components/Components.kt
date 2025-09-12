package com.example.projectdroid.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectdroid.ui.theme.Cpu
import com.example.projectdroid.ui.theme.DarkRed
import com.example.projectdroid.ui.theme.LightGreeen
import com.example.projectdroid.ui.theme.PrimaryFontSize
import com.example.projectdroid.ui.theme.SecondaryBackground
import com.example.projectdroid.ui.theme.SecondaryFontSize
import com.example.projectdroid.ui.theme.ServerIcon
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.DpSize
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.projectdroid.ui.theme.AppHorizontalPadding

@Composable
fun DisplayCard(
    topText: String,
    bottomText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8))
            .background(SecondaryBackground)
            .padding(10.dp)
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

@Composable
fun DisplayMinerStatus( Title: String, Status: String, HashRate: String, Tempurature: String, onClick: () -> Any) {
    Box (
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8))
            .background(SecondaryBackground)
            .clickable{
                onClick()
            }

            .padding(10.dp)
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
                        "sdgsd",
                        modifier = Modifier.size(20.dp)
                        ,
                        tint = Color.White
                    )
                }
                Column {
                    Text(Title, color = Color.LightGray, fontSize = PrimaryFontSize)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(Status,
                        color = if (Status == "Online") Color.Green else Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(if (Status == "Online") Color.Green.copy(alpha = 0.1f) else DarkRed.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Column (
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(HashRate, color = Color.LightGray, fontSize = PrimaryFontSize)
                Spacer(modifier = Modifier.height(6.dp))
                Text(Tempurature, color = Color.White, fontSize = SecondaryFontSize)
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
                modifier = Modifier.padding(vertical = 15.dp, horizontal = AppHorizontalPadding)
            ) {
                Text("Go Back")
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

        onDispose {}
    }
}
