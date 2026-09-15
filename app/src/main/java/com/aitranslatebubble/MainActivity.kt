package com.aitranslatebubble

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.aitranslatebubble.service.TranslationOverlayService
import com.aitranslatebubble.ui.HomeScreen
import com.aitranslatebubble.ui.theme.AITranslateBubbleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AITranslateBubbleTheme {
                var isServiceRunning by remember { mutableStateOf(false) }

                HomeScreen(
                    isServiceRunning = isServiceRunning,
                    onToggleService = { start ->
                        if (start) {
                            if (checkOverlayPermission()) {
                                startOverlayService()
                                isServiceRunning = true
                            } else {
                                requestOverlayPermission()
                            }
                        } else {
                            stopOverlayService()
                            isServiceRunning = false
                        }
                    }
                )
            }
        }
    }

    private fun checkOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }

    private fun startOverlayService() {
        val intent = Intent(this, TranslationOverlayService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopOverlayService() {
        val intent = Intent(this, TranslationOverlayService::class.java)
        stopService(intent)
    }
}
