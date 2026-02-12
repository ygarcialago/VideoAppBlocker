package com.example.videoappblocker.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    fun getInstalledUserApps(): List<String> {
        val pm = application.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val installedAppsNames = mutableListOf<String>()

        for (app in apps) {
            if (app.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val label = pm.getApplicationLabel(app).toString()
                installedAppsNames.add(label)
            }
        }
        return installedAppsNames
    }
}

