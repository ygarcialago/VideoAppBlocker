package com.example.videoappblocker.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoappblocker.repository.dataStore
import com.example.videoappblocker.ui.theme.VideoAppBlockerTheme
import com.example.videoappblocker.utils.canDrawOverlays
import com.example.videoappblocker.utils.isAccessibilityServiceEnabled
import com.example.videoappblocker.viewmodel.AppListViewModel
import com.example.videoappblocker.viewmodel.VideoSettingsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun MainContent() {

    VideoAppBlockerTheme {

        val context = LocalContext.current
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()

        val hasPermission = remember {
            mutableStateOf(isAccessibilityServiceEnabled(context) && canDrawOverlays(context))
        }

        val HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")


        suspend fun setOnboardingSeen(context: Context) {
            context.dataStore.edit { preferences ->
                preferences[HAS_SEEN_ONBOARDING] = true
            }
        }

        val hasSeenOnboardingFlow: Flow<Boolean> = context.dataStore.data
            .map { preferences ->
                preferences[HAS_SEEN_ONBOARDING] ?: false
            }
        val hasSeenOnboarding by hasSeenOnboardingFlow.collectAsState(initial = null)

        LaunchedEffect(Unit) {
            hasPermission.value = (isAccessibilityServiceEnabled(context) && canDrawOverlays(context))
        }

        val viewModel: AppListViewModel = viewModel()
        val videoViewModel: VideoSettingsViewModel = viewModel()

        val startDestination = when {
            hasSeenOnboarding == false -> "first_launch_screen"
            hasPermission.value -> "main"
            else -> "accessibility_required"
        }

        if (hasSeenOnboarding == null) {
            return@VideoAppBlockerTheme
        }
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = startDestination,
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

                composable ("first_launch_screen") {
                    FirstLaunchScreen { scope.launch { setOnboardingSeen(context) } }
                }
            }
        }
    }
}