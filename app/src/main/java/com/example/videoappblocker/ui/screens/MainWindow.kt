package com.example.videoappblocker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.videoappblocker.viewmodel.AppListViewModel
import androidx.navigation.NavController
import com.example.videoappblocker.ui.components.TimerScreen

@Composable
fun MainWindow(navController: NavController, viewModel: AppListViewModel) {
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "VideoAppBlocker",
            style = MaterialTheme.typography.headlineLarge
        )

        TimerScreen()

        Button(
            onClick = {
                viewModel.loadInstalledApps {
                    navController.navigate("app_list")
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Modificar lista")
            }
        }

    }
}
