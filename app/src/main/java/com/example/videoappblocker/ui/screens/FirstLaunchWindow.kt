package com.example.videoappblocker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FirstLaunchScreen(
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Controla tu tiempo en las apps más adictivas",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "VideoAppBlocker te ayuda a evitar distracciones bloqueando apps cuando superas el tiempo que tú elijas.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.Start){
                FeatureItem("⏱️", "Cuenta el tiempo que usas apps de video")
                FeatureItem("🚫", "Bloquea las apps cuando superas el límite")
                FeatureItem("📊", "Te ayuda a controlar tu tiempo")
                FeatureItem("📹", "Pon un pequeño vídeo/gif recordándote por qué no debes entrar a la app")
            }
        }

        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Empezar")
        }
    }
}

@Composable
fun FeatureItem(icon: String, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text)
    }
}