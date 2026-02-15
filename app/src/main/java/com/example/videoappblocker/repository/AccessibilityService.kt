package com.example.videoappblocker.repository

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.*
import androidx.datastore.preferences.core.stringSetPreferencesKey

class AppBlockAccessibilityService : AccessibilityService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val SELECTED_APPS_KEY =
        stringSetPreferencesKey("selected_apps")

    private var blockedApps: Set<String> = emptySet()

    override fun onServiceConnected() {
        super.onServiceConnected()

        serviceScope.launch {
            applicationContext.dataStore.data.collect { prefs ->
                blockedApps = prefs[SELECTED_APPS_KEY] ?: emptySet()
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
            return

        val packageName = event.packageName?.toString() ?: return
        Log.d("Access app check", "App checked: $packageName")

        if (blockedApps.contains(packageName)) {
            Log.d("App block", "App bloqueada ${packageName}")
            goToHome()
        }
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun goToHome() {
        performGlobalAction(GLOBAL_ACTION_HOME)
    }
}
