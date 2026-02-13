package com.example.videoappblocker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoappblocker.viewmodel.TimerViewModel

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel()) {

    val timeLeft by viewModel.timeLeft
    val isRunning by viewModel.isRunning

    var inputTime by remember { mutableStateOf("") }

    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Tiempo restante: ${viewModel.formattedTime}",
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = hours,
                onValueChange = { hours = it.filter { c -> c.isDigit() } },
                label = { Text("HH") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = minutes,
                onValueChange = { minutes = it.filter { c -> c.isDigit() } },
                label = { Text("MM") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = seconds,
                onValueChange = { seconds = it.filter { c -> c.isDigit() } },
                label = { Text("SS") },
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    val h = hours.toIntOrNull() ?: 0
                    val m = (minutes.toIntOrNull() ?: 0).coerceIn(0, 59)
                    val s = (seconds.toIntOrNull() ?: 0).coerceIn(0, 59)

                    val totalSeconds = h * 3600 + m * 60 + s
                    viewModel.startTimer(totalSeconds)
                },
                enabled = !isRunning
            ) {
                Text("Start")
            }

            Button(
                onClick = { viewModel.resetTimer() }
            ) {
                Text("Stop")
            }
        }

    }
}