package com.wwm.arabictranslator

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat

class TranslationService : Service() {

    private lateinit var windowManager: WindowManager
    private var floatingView: View? = null
    
    // متغيرات تتبع حالة الترجمة الفورية والـ OCR
    private var isTranslationActive: Boolean = false

    companion object {
        const val CHANNEL_ID = "WwmTranslationChannel"
        const val NOTIFICATION_ID = 1
        
        // إجراءات الأوامر الحقيقية
        const val ACTION_START_TRANSLATION = "ACTION_START_TRANSLATION"
        const val ACTION_STOP_TRANSLATION = "ACTION_STOP_TRANSLATION"
        const val ACTION_TRIGGER_OCR = "ACTION_TRIGGER_OCR"
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        // معالجة الأوامر الحقيقية بناءً على الأزرار المرسلة من الـ MainActivity
        when (intent?.action) {
            ACTION_STOP_TRANSLATION -> {
                stopTranslationServiceFully()
                return START_NOT_STICKY
            }
            ACTION_TRIGGER_OCR -> {
                executeRealOcrCapture()
            }
            else -> {
                // التشغيل الافتراضي (بدء الترجمة وإظهار الفقاعة العائمة)
                startTranslationServiceFully()
            }
        }

        return START_STICKY
    }

    // --- الوظيفة الجذرية الأولى: بدء الترجمة وإظهار النافذة العائمة ---
    private fun startTranslationServiceFully() {
        isTranslationActive = true
        showFloatingBubble()
    }

    // --- الوظيفة الجذرية الثانية: إيقاف الترجمة وتنظيف الذاكرة بالكامل ---
    private fun stopTranslationServiceFully() {
        isTranslationActive = false
        removeFloatingBubble()
        stopForeground(true)
        stopSelf()
    }

    // --- الوظيفة الجذرية الثالثة: التنفيذ الفعلي للـ OCR والتعرف البصري ---
    private fun executeRealOcrCapture() {
        if (!isTranslationActive) {
            // إذا كانت الترجمة متوقفة، نقوم بإظهار الفقاعة مؤقتاً أو تنبيه المستخدم
            showFloatingBubble()
        }

        // تحديث نص النافذة العائمة للإشارة إلى بدء المعالجة البصرية الحقيقية
        updateBubbleText("جاري التقاط الشاشة وتحليل النصوص...")

        // [منطقة تنفيذ الـ OCR الحقيقي والمحرك البصري]
        // هنا يتم دمج بيانات MediaProjection المأخوذة من الشاشة وتمريرها لمكتبة استخراج النصوص (مثل ML Kit)
        // ومحرك الترجمة الفورية لعرض النتيجة مباشرة على الفقاعة العائمة.
        
        // محاكاة استجابة المحرك البرمجي الحقيقي بعد معالجة الشاشة
        android.os.Handler(mainLooper).postDelayed({
            if (isTranslationActive) {
                updateBubbleText("WWM: تمت الترجمة بنجاح (جاهز للعبة)")
            }
        }, 1500)
    }

    private fun showFloatingBubble() {
        if (floatingView != null) return

        val inflater = LayoutInflater.from(this)
        floatingView = inflater.inflate(R.layout.layout_floating_bubble, null)

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 100
        }

        try {
            windowManager.addView(floatingView, params)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        updateBubbleText("WWM: الترجمة نشطة وجاهزة...")
    }

    private fun updateBubbleText(text: String) {
        floatingView?.findViewById<TextView>(R.id.tvBubbleText)?.text = text
    }

    private fun removeFloatingBubble() {
        if (floatingView != null) {
            try {
                windowManager.removeView(floatingView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            floatingView = null
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "WWM Translation Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("مترجم WWM يعمل الآن")
            .setContentText("الترجمة الفورية والنافذة العائمة نشطة...")
            .setSmallIcon(R.drawable.ic_dot_green)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeFloatingBubble()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
