package com.example.videoappblocker.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoappblocker.repository.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.emptySet

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    private val _apps = mutableStateOf<List<AppInfoUi>>(emptyList())
    private val dataStore = application.dataStore
    private val SELECTED_APPS_KEY = stringSetPreferencesKey("selected_apps")

    val apps: State<List<AppInfoUi>> = _apps
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedApps = mutableStateOf<Set<String>>(emptySet())
    val selectedApps: State<Set<String>> = _selectedApps

    fun loadSelections() {
        viewModelScope.launch {
            val saved = dataStore.data.first()[SELECTED_APPS_KEY] ?: emptySet()
            _selectedApps.value = saved
        }
    }

    fun toggleAppSelection(packageName: String) {
        _selectedApps.value =
            if (_selectedApps.value.contains(packageName)) {
                _selectedApps.value - packageName
            } else {
                _selectedApps.value + packageName
            }

        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[SELECTED_APPS_KEY] = _selectedApps.value
            }
        }
    }

    fun getBlockedApps(): List<String> {
        return _selectedApps.value.toList()
    }

    fun loadInstalledApps(onFinished: () -> Unit = {}) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true

            val result = withContext(Dispatchers.IO) {

                val application = getApplication<Application>()
                val pm = application.packageManager
                val myPackageName = application.packageName

                val allApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)

                allApps
                    .filter { appInfo ->
                        // Solo apps lanzables
                        pm.getLaunchIntentForPackage(appInfo.packageName) != null &&
                                // No incluir tu propia app
                                appInfo.packageName != myPackageName
                    }
                    .map { appInfo ->
                        AppInfoUi(
                            label = pm.getApplicationLabel(appInfo).toString(),
                            packageName = appInfo.packageName
                        )
                    }
                    .sortedBy { it.label.lowercase() }
            }

            _apps.value = result
            _isLoading.value = false

            onFinished()
        }
    }

    data class AppInfoUi(
        val label: String,
        val packageName: String
    )
}

