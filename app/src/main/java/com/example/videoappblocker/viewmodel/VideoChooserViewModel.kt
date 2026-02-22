package com.example.videoappblocker.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoappblocker.repository.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class VideoSettingsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val dataStore = application.dataStore

    private val SELECTED_VIDEO_KEY =
        stringPreferencesKey("selected_video_uri")

    private val _selectedVideoUri = mutableStateOf<Uri?>(null)
    val selectedVideoUri: State<Uri?> = _selectedVideoUri

    fun saveVideoUri(uri: Uri) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[SELECTED_VIDEO_KEY] = uri.toString()
            }
            _selectedVideoUri.value = uri
        }
    }

    fun loadSavedVideo() {
        viewModelScope.launch {
            val savedUriString =
                dataStore.data.first()[SELECTED_VIDEO_KEY]

            _selectedVideoUri.value =
                savedUriString?.toUri()
        }
    }

    suspend fun getSavedVideoUriString(): String? {
        return dataStore.data.first()[SELECTED_VIDEO_KEY]
    }

    fun clearSavedVideo() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs.remove(SELECTED_VIDEO_KEY)
            }
            _selectedVideoUri.value = null
        }
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null

        val cursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }

        return name
    }
}
