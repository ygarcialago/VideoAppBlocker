package com.example.videoappblocker.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    private val _apps = mutableStateOf<List<String>>(emptyList())
    val apps: State<List<String>> = _apps

    fun loadInstalledApps() {
        val pm = getApplication<Application>().packageManager
        val apps = pm.getInstalledApplications(0)

        val userApps = apps
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 }
            .map { pm.getApplicationLabel(it).toString() }

        _apps.value = userApps
    }
}

