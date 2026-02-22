package com.example.videoappblocker.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "blocked_apps")
