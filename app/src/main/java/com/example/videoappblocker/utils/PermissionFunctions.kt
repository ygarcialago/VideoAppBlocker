package com.example.videoappblocker.utils

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import com.example.videoappblocker.repository.AppBlockAccessibilityService

fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val expectedComponentName = ComponentName(
        context,
        AppBlockAccessibilityService::class.java
    )

    val enabledServicesSetting = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    return enabledServicesSetting.split(":")
        .map { ComponentName.unflattenFromString(it) }
        .contains(expectedComponentName)
}

fun canDrawOverlays(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}