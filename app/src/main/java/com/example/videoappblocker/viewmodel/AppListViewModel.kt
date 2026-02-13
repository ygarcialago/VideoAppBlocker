package com.example.videoappblocker.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    private val _apps = mutableStateOf<List<String>>(emptyList())
    val apps: State<List<String>> = _apps
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadInstalledApps(onFinished: () -> Unit = {}) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                val pm = getApplication<Application>().packageManager
                val allApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)

                Log.d("AppCheck", "Total: ${allApps.size}")

                allApps.filter { appInfo ->
                    pm.getLaunchIntentForPackage(appInfo.packageName) != null
                }.map { appInfo ->
                    pm.getApplicationLabel(appInfo).toString()
                }.sortedBy { it.lowercase() }
            }

            _apps.value = result
            _isLoading.value = false

            onFinished()
        }
    }
}

