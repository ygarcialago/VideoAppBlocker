package com.example.videoappblocker.ui.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.videoappblocker.viewmodel.VideoSettingsViewModel

@Composable
fun VideoChose(viewModel: VideoSettingsViewModel) {

    val context = LocalContext.current
    val selectedVideoUri by viewModel.selectedVideoUri

    LaunchedEffect(Unit) {
        viewModel.loadSavedVideo()
    }

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        uri?.let {

            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            viewModel.saveVideoUri(it)
        }
    }
    val fileName = selectedVideoUri?.let {
        viewModel.getFileName(context, it)
    }


    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Button(
            onClick = {
                videoPickerLauncher.launch(arrayOf("video/*"))
            }
        ) {

            Text(fileName ?: "Selecciona un vídeo")
        }
        if (selectedVideoUri != null) {
            Button(onClick = {
                viewModel.clearSavedVideo()
                }
            ) {
                Text("Limpiar selección")
            }
        }
    }

}

