package com.wwm.arabictranslator.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.wwm.arabictranslator.ui.OverlayManager

class TranslatorService : Service() {

    private var overlayManager: OverlayManager? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        overlayManager = OverlayManager(this)
        overlayManager?.showOverlay()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "wwm_translator_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WWM Translator Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("WWM Arabic Translator")
            .setContentText("المترجم يعمل الآن فوق الشاشة...")
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                startForeground(101, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION)
            } catch (e: Exception) {
                startForeground(101, notification)
            }
        } else {
            startForeground(101, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayManager?.removeOverlay()
    }
}
