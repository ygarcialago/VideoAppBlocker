package com.example.videoappblocker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoappblocker.ui.screens.AppListWindow
import com.example.videoappblocker.ui.screens.MainWindow
import com.example.videoappblocker.ui.theme.VideoAppBlockerTheme
import com.example.videoappblocker.viewmodel.AppListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoAppBlockerTheme {

                val navController = rememberNavController()
                val viewModel: AppListViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("main") {
                            MainWindow(navController, viewModel)
                        }

                        composable("app_list") {
                            AppListWindow(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}