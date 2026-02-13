package com.example.videoappblocker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.videoappblocker.viewmodel.AppListViewModel

@Composable
fun AppListWindow(navController: NavController, viewModel: AppListViewModel) {
    val apps by viewModel.apps

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "Apps instaladas",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(apps) { appName ->
                Text(
                    text = appName,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Volver")
        }
    }
}