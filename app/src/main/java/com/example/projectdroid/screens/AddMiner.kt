package com.example.projectdroid.screens

import MinerType
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.projectdroid.components.BackButtonWithTooltip
import com.example.projectdroid.components.SetStatusBarContentColor
import com.example.projectdroid.repo.insertMiner
import com.example.projectdroid.repo.loadMiners
import com.example.projectdroid.repo.removeMinerAt
import com.example.projectdroid.ui.theme.AppHorizontalPadding
import com.example.projectdroid.ui.theme.AppRoundedDp
import com.example.projectdroid.ui.theme.LightGreeen
import com.example.projectdroid.ui.theme.PortNumberIcon
import com.example.projectdroid.ui.theme.PrimaryBackground
import com.example.projectdroid.ui.theme.PrimaryButtonBackground
import com.example.projectdroid.ui.theme.PrimaryFontSize
import com.example.projectdroid.ui.theme.SecondaryBackground
import com.example.projectdroid.ui.theme.SecondaryFontSize
import com.example.projectdroid.ui.theme.ServerIcon
import com.example.projectdroid.utils.ValidIpv4
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMinerScreen(context: Context, navController: NavHostController) {
    SetStatusBarContentColor(false)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var parentWidth by remember { mutableStateOf(0.dp) }
    var ipv4 by remember { mutableStateOf("") }
    var portNum by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var isIpv4Error by remember {mutableStateOf(false)}
    var ipv4ErrMsg by remember { mutableStateOf("") }

    var isPortError by remember {mutableStateOf(false)}
    var portErrMsg by remember { mutableStateOf("") }

    var isNameError by remember {mutableStateOf(false)}
    var nameErrMsg by remember { mutableStateOf("") }

    var isDropDown by remember { mutableStateOf(false) }
    var selectedType by remember {mutableStateOf("Antminer")}

    Scaffold (
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Miner", fontSize = PrimaryFontSize)

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
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(PrimaryBackground),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    modifier = Modifier.padding(AppHorizontalPadding)
                ) {
                    Column (
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("IP Address", color = Color.LightGray, fontSize = SecondaryFontSize)
                        TextField(
                            value = ipv4,
                            onValueChange = {
                                ipv4 = it
                                isIpv4Error = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(AppRoundedDp),

                                    placeholder = { Text("192.168.1.100 ...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = ServerIcon, // 🌐 example icon
                                    contentDescription = "IP Address",
                                    tint = Color.LightGray
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = isIpv4Error,
                            supportingText = {
                                if (isIpv4Error) {
                                    Text(ipv4ErrMsg, color = Color.Red)
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SecondaryBackground,
                                unfocusedContainerColor = SecondaryBackground,
                                unfocusedPlaceholderColor = Color.LightGray,
                                focusedPlaceholderColor = Color.LightGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.LightGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorContainerColor = SecondaryBackground,
                                errorTextColor = Color.LightGray,
                                errorIndicatorColor = Color.Transparent,
                                errorPlaceholderColor = Color.Red
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column (
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Port Number", color = Color.LightGray, fontSize = SecondaryFontSize)
                        TextField(
                            value = portNum,
                            onValueChange = { newValue ->
                                if (newValue.isDigitsOnly()) { // Check if the new value contains only digits
                                    portNum = newValue
                                    isPortError = false
                                }

                                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(AppRoundedDp),
                            maxLines = 1,

                            placeholder = { Text("Example 4028") },
                            leadingIcon = {
                                Icon(
                                    imageVector = PortNumberIcon, // 🌐 example icon
                                    contentDescription = "IP Address",
                                    tint = Color.LightGray
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = isPortError,
                            supportingText = {
                                if (isPortError) {
                                    Text(portErrMsg, color = Color.Red)
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SecondaryBackground,
                                unfocusedContainerColor = SecondaryBackground,
                                unfocusedPlaceholderColor = Color.LightGray,
                                focusedPlaceholderColor = Color.LightGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.LightGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorContainerColor = SecondaryBackground,
                                errorTextColor = Color.LightGray,
                                errorIndicatorColor = Color.Transparent,
                                errorPlaceholderColor = Color.Red
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column (
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Name", color = Color.LightGray, fontSize = SecondaryFontSize)
                        TextField(
                            value = name,
                            onValueChange = {
                                name = it
                                isNameError = false
                                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(AppRoundedDp),

                            placeholder = { Text("e.g Antminer S19") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.List, // 🌐 example icon
                                    contentDescription = "IP Address",
                                    tint = Color.LightGray
                                )
                            },
                            isError = isNameError,
                            supportingText = {
                                if (isNameError) {
                                    Text(nameErrMsg, color = Color.Red)
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SecondaryBackground,
                                unfocusedContainerColor = SecondaryBackground,
                                unfocusedPlaceholderColor = Color.LightGray,
                                focusedPlaceholderColor = Color.LightGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.LightGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorContainerColor = SecondaryBackground,
                                errorTextColor = Color.LightGray,
                                errorIndicatorColor = Color.Transparent,
                                errorPlaceholderColor = Color.Red
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column (
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Type", color = Color.LightGray, fontSize = SecondaryFontSize)
                        Box(modifier = Modifier.wrapContentSize()) {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(AppRoundedDp))
                                    .background(SecondaryBackground)
                                    .clickable{
                                        isDropDown = true
                                    }
                                    .padding(14.dp)
                                ,

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("$selectedType", color = Color.LightGray)
                                Icon(
                                    if (isDropDown) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    "drop",
                                    tint = Color.White
                                )
                            }

                                DropdownMenu(
                                    expanded = isDropDown,
                                    onDismissRequest = { isDropDown = false },
                                    modifier = Modifier
                                    ,
                                    offset = DpOffset(x = (-20).dp, y = 20.dp)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Antminer") },
                                        modifier = Modifier.fillMaxWidth(),

                                        onClick = {
                                            // Handle Option 1 click
                                            selectedType = "Antminer"
                                            isDropDown = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Whatsminer") },
                                        modifier = Modifier.fillMaxWidth(),

                                        onClick = {
                                            // Handle Option 2 click
                                            selectedType = "Whatsminer"

                                            isDropDown = false
                                        }
                                    )
                                }

                        }
                    }

                }

                Column (
                    modifier = Modifier.padding(AppHorizontalPadding)
                )  {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        shape = RoundedCornerShape(7.dp), // <-- Make it not rounded
                        onClick = {
                            if (ipv4.isEmpty() && portNum.isEmpty() && name.isEmpty()) {
                                isIpv4Error = true
                                ipv4ErrMsg = "ipv4 is empty"

                                isPortError = true
                                portErrMsg = "port number is empty"

                                isNameError = true
                                nameErrMsg = "name is empty"

                                return@Button
                            }
                            if (ipv4.isEmpty()) {
                                isIpv4Error = true
                                ipv4ErrMsg = "ipv4 is empty"
                                return@Button
                            }
                            if (!ValidIpv4(ipv4)) {
                                isIpv4Error = true
                                ipv4ErrMsg = "ipv4 is not valid"
                                return@Button
                            }
                            if (portNum.isEmpty()) {
                                isPortError = true
                                portErrMsg = "port number is empty"
                                return@Button
                            }
                            if (name.isEmpty()) {
                                isNameError = true
                                nameErrMsg = "name is empty"
                                return@Button
                            }

                            // inesert
                            val newMiner = MinerType(
                                ipv4,
                                portNum,
                                name,
                                selectedType
                            )
                            insertMiner(context, newMiner)

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Operation successful!",
                                    actionLabel = "Dismiss" // Optional: for user to dismiss
                                )
                            }

                            navController.popBackStack()
                        },
                        contentPadding = PaddingValues(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGreeen, // Purple as an example
                            contentColor = Color.Black,

                            ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 10.dp
                        )
                    ) {
                        Text("Add Miner", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

