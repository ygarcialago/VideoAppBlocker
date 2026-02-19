package com.example.videoappblocker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class TimerViewModel : ViewModel() {

    private val _timeLeft = mutableStateOf(0)
    var strictMode = mutableStateOf(false)

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    val formattedTime: String
        get() {
            val hours = _timeLeft.value / 3600
            val minutes = (_timeLeft.value % 3600) / 60
            val seconds = _timeLeft.value % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

    private var timerJob: Job? = null

    fun startTimer(totalSeconds: Int) {
        if (_isRunning.value || totalSeconds <= 0) return

        timerJob?.cancel()

        _timeLeft.value = totalSeconds
        _isRunning.value = true

        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value -= 1
            }
            _isRunning.value = false
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _isRunning.value = false
        _timeLeft.value = 0
    }
}
