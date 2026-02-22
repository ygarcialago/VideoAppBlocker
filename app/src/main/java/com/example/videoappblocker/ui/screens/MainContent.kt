package com.example.videoappblocker.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoappblocker.ui.theme.VideoAppBlockerTheme
import com.example.videoappblocker.utils.canDrawOverlays
import com.example.videoappblocker.utils.isAccessibilityServiceEnabled
import com.example.videoappblocker.viewmodel.AppListViewModel
import com.example.videoappblocker.viewmodel.VideoSettingsViewModel

@Composable
fun MainContent() {

    VideoAppBlockerTheme {

        val context = LocalContext.current
        val navController = rememberNavController()

        val hasPermission = remember {
            mutableStateOf(isAccessibilityServiceEnabled(context) && canDrawOverlays(context))
        }

        LaunchedEffect(Unit) {
            hasPermission.value = (isAccessibilityServiceEnabled(context) && canDrawOverlays(context))
        }

        val viewModel: AppListViewModel = viewModel()
        val videoViewModel: VideoSettingsViewModel = viewModel()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = if (hasPermission.value) "main" else "accessibility_required",
                modifier = Modifier.padding(innerPadding)
            ) {

                composable("accessibility_required") {
                    PermissionOnboardingScreen(navController)
                }

                composable("main") {
                    MainWindow(navController, viewModel, videoViewModel)
                }

                composable("app_list") {
                    AppListWindow(navController, viewModel)
                }
            }
        }
    }
}