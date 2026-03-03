package com.example.videoappblocker.repository

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import androidx.media3.ui.AspectRatioFrameLayout
import android.view.accessibility.AccessibilityEvent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.*
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.flow.first

@SuppressLint("AccessibilityPolicy")

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

    private var overlayAdded = false

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun goToHome() {
        if (overlayAdded) return

        performGlobalAction(GLOBAL_ACTION_HOME)

        Handler(Looper.getMainLooper()).post {
            CoroutineScope(Dispatchers.Main).launch {

                val prefs = applicationContext.dataStore.data.first()
                val uriString = prefs[stringPreferencesKey("selected_video_uri")]

                // 🔥 SOLO si hay URI guardada
                if (!uriString.isNullOrEmpty()) {

                    try {
                        val wm = getSystemService(WINDOW_SERVICE) as WindowManager

                        val playerView = PlayerView(this@AppBlockAccessibilityService).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT
                            )
                            useController = false
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                        }

                        val player = ExoPlayer.Builder(this@AppBlockAccessibilityService).build()
                        playerView.player = player

                        val mediaItem = MediaItem.fromUri(uriString.toUri())
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        player.playWhenReady = true
                        player.repeatMode = ExoPlayer.REPEAT_MODE_OFF

                        player.addListener(object : androidx.media3.common.Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                if (playbackState == androidx.media3.common.Player.STATE_ENDED) {
                                    wm.removeView(playerView)
                                    player.release()
                                    overlayAdded = false
                                    Log.d("AppBlocker", "Video finalizado, overlay eliminado")
                                }
                            }
                        })

                        val params = WindowManager.LayoutParams(
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                            PixelFormat.TRANSLUCENT
                        )

                        wm.addView(playerView, params)
                        overlayAdded = true

                        Log.d("AppBlocker", "Overlay del reproductor añadido sobre Home")

                    } catch (e: Exception) {
                        Log.e("AppBlocker", "Error al abrir overlay: ${e.message}")
                    }

                } else {
                    Log.d("AppBlocker", "No hay URI guardada, no se muestra overlay")
                }
            }
        }
    }
}
