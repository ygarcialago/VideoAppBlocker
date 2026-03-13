package com.example.videoappblocker.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoappblocker.viewmodel.TimerViewModel

@Composable
fun TimerScreen(viewModel: TimerViewModel) {

    val isRunning by viewModel.isRunning

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
        Spacer(modifier = Modifier.height(16.dp))

        TimerPicker(viewModel, isRunning)

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = viewModel.strictMode.value,
                onClick = { viewModel.strictMode.value = !viewModel.strictMode.value },
                enabled = !isRunning
            )
            Text("Bloquear Stop")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerPicker(viewModel: TimerViewModel, isRunning: Boolean) {

    val state = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            TimeInput(
                state = state
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                val totalSeconds = state.hour * 3600 + state.minute * 60
                viewModel.startTimer(totalSeconds)
            }) {
                Text("Start")
            }
            Button(
                onClick = { viewModel.resetTimer() },
                enabled = !(viewModel.strictMode.value && isRunning)

            ) {
                Text("Stop")
            }
        }

    }
}