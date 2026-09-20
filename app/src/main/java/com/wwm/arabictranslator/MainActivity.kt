package com.wwm.arabictranslator

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tvStatus: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)

        // --- الزر الأول: بدء الترجمة الفورية بشكل حقيقي وعميق ---
        findViewById<Button>(R.id.btnStartTranslation)?.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "يرجى منح إذن العرض فوق التطبيقات لتمكين الترجمة", Toast.LENGTH_LONG).show()
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "جاري تفعيل الترجمة الفورية...", Toast.LENGTH_SHORT).show()
                tvStatus?.text = "الحالة: تعمل بنجاح"
                
                // إرسال أمر البدء الحقيقي للخدمة الخلفية
                val serviceIntent = Intent(this, TranslationService::class.java).apply {
                    action = TranslationService.ACTION_START_TRANSLATION
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                } else {
                    startService(serviceIntent)
                }
            }
        }

        // --- الزر الثاني: إيقاف الترجمة الفورية وتنظيف الذاكرة بشكل كامل ---
        findViewById<Button>(R.id.btnStopTranslation)?.setOnClickListener {
            Toast.makeText(this, "جاري إيقاف الترجمة...", Toast.LENGTH_SHORT).show()
            tvStatus?.text = "الحالة: متوقفة"
            
            // إرسال أمر الإيقاف الحقيقي للخدمة الخلفية
            val serviceIntent = Intent(this, TranslationService::class.java).apply {
                action = TranslationService.ACTION_STOP_TRANSLATION
            }
            startService(serviceIntent)
        }

        // --- الزر الثالث: التقاط الشاشة وتشغيل التعرف البصري (OCR) والترجمة الفورية الحقيقية ---
        findViewById<Button>(R.id.btnTriggerOcr)?.setOnClickListener {
            Toast.makeText(this, "جاري التقاط الشاشة وتحليل النصوص (OCR)...", Toast.LENGTH_SHORT).show()
            tvStatus?.text = "الحالة: جاري مسح النصوص..."
            
            // إرسال أمر الـ OCR الحقيقي للخدمة الخلفية
            val serviceIntent = Intent(this, TranslationService::class.java).apply {
                action = TranslationService.ACTION_TRIGGER_OCR
            }
            startService(serviceIntent)
        }
    }
}
