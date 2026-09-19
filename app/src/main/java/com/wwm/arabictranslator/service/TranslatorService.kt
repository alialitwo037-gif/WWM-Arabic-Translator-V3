package com.wwm.arabictranslator.service

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.wwm.arabictranslator.ocr.OcrEngine
import com.wwm.arabictranslator.translation.TranslationEngine
import com.wwm.arabictranslator.ui.OverlayManager
import kotlinx.coroutines.*

class TranslatorService : Service() {

    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var overlayManager: OverlayManager? = null

    private val ocrEngine = OcrEngine()
    private val translationEngine = TranslationEngine()
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())

    private var lastProcessedText = ""
    @Volatile private var isProcessing = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        overlayManager = OverlayManager(this)
        overlayManager?.showOverlay()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val resultCode = intent?.getIntExtra("RESULT_CODE", Activity.RESULT_CANCELED) ?: Activity.RESULT_CANCELED
        val dataIntent = intent?.getParcelableExtra<Intent>("DATA_INTENT")

        if (resultCode == Activity.RESULT_OK && dataIntent != null) {
            setupMediaProjection(resultCode, dataIntent)
        }

        return START_STICKY
    }

    private fun setupMediaProjection(resultCode: Int, data: Intent) {
        val mpManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        mediaProjection = mpManager.getMediaProjection(resultCode, data)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)

        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val density = metrics.densityDpi

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)

        imageReader?.setOnImageAvailableListener({ reader ->
            if (isProcessing) {
                // إغلاق الإطارات التلقائية الفائضة لمنع الذاكرة من الامتلاء
                val image = try { reader.acquireLatestImage() } catch (e: Exception) { null }
                image?.close()
                return@setOnImageAvailableListener
            }

            var image: Image? = null
            try {
                image = reader.acquireLatestImage()
            } catch (e: Exception) {
                image = null
            }

            if (image == null) return@setOnImageAvailableListener

            isProcessing = true

            val currentImage = image
            serviceScope.launch(Dispatchers.Default) {
                try {
                    val planes = currentImage.planes
                    val buffer = planes[0].buffer
                    val pixelStride = planes[0].pixelStride
                    val rowStride = planes[0].rowStride
                    val rowPadding = rowStride - pixelStride * width

                    val bitmap = Bitmap.createBitmap(
                        width + rowPadding / pixelStride,
                        height,
                        Bitmap.Config.ARGB_8888
                    )
                    bitmap.copyPixelsFromBuffer(buffer)
                    currentImage.close()

                    // اقتصاص العرض الفعلي وإزالة padding الأفقية لضمان مطابقة الأبعاد
                    val cleanBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height)

                    ocrEngine.processImage(cleanBitmap) { detectedText ->
                        if (detectedText.isNotEmpty() && detectedText != lastProcessedText) {
                            lastProcessedText = detectedText
                            serviceScope.launch(Dispatchers.IO) {
                                val translatedText = translationEngine.translate(detectedText)
                                withContext(Dispatchers.Main) {
                                    overlayManager?.updateTranslationText(translatedText)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    try { currentImage.close() } catch (_: Exception) {}
                } finally {
                    delay(1500)
                    isProcessing = false
                }
            }
        }, Handler(Looper.getMainLooper()))

        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "ScreenCapture",
            width, height, density,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader?.surface, null, null
        )
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
            .setContentText("المترجم يقرأ الشاشة الآن...")
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
        serviceScope.cancel()
        virtualDisplay?.release()
        mediaProjection?.stop()
        overlayManager?.removeOverlay()
    }
}
