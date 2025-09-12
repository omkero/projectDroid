package com.example.projectdroid.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.projectdroid.ui.theme.PrimaryBackground

@Composable
fun IndexScreen() {

    val navController = rememberNavController()
    val isDark = isSystemInDarkTheme()

// At the top of your MainScreen or in a ViewModel (if you want to control it globally)
    val transitionInDuration = 200 // For forward animations (navigate to screen)
    val transitionOutDuration = 200 // For fadeOut or slideOut (same forward)
    val popTransitionDuration = 300 // Slightly longer for smoother backward transition

    // Once data is loaded, determine the start destination
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground) // your app background
    ) {
        NavHost(navController, "HomeScreen") {

            composable(
                route = "HomeScreen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(durationMillis = transitionInDuration, easing = LinearOutSlowInEasing)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = transitionInDuration, easing = LinearEasing)
                    )
                },

                exitTransition = {
                    fadeOut(
                        animationSpec = tween(durationMillis = transitionOutDuration, easing = LinearEasing)
                    )
                },

                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearOutSlowInEasing)
                    )
                },

                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearOutSlowInEasing)
                    ) + fadeOut(
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearEasing)
                    )
                }

            ) {
                HomeScreen(LocalContext.current, navController)
            }

            composable(
                route = "MinerDetailsScreen",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(durationMillis = transitionInDuration, easing = LinearOutSlowInEasing)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = transitionInDuration, easing = LinearEasing)
                    )
                },

                exitTransition = {
                    fadeOut(
                        animationSpec = tween(durationMillis = transitionOutDuration, easing = LinearEasing)
                    )
                },

                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearOutSlowInEasing)
                    )
                },

                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearOutSlowInEasing)
                    ) + fadeOut(
                        animationSpec = tween(durationMillis = popTransitionDuration, easing = LinearEasing)
                    )
                }

            ) {
                MinerDetailsScreen(LocalContext.current, navController)
            }
        }
    }
}
