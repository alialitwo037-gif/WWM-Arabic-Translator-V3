package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.wwm.arabictranslator.utils.UiGenerator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // تطبيق التدرجات والأشكال الذكية برمجياً بأمان
        val headerCard = findViewById<LinearLayout>(R.id.headerCardContainer)
        headerCard?.let {
            UiGenerator.applyHeaderGradient(it)
        }

        // ربط وظيفة زر بدء الترجمة (الزر رقم 1)
        findViewById<Button>(R.id.btnStartTranslation)?.setOnClickListener {
            // التحقق من صلاحيات النافذة العائمة (Overlay Permission) بأمان
            if (!android.provider.Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    android.net.Uri.parse("package:$packageName")
                )
                startActivity(intent)
            } else {
                // بدء خدمة الترجمة العائمة
                val serviceIntent = Intent(this, com.wwm.arabictranslator.services.ScreenTranslateService::class.java)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                } else {
                    startService(serviceIntent)
                }
            }
        }

        // ربط وظيفة زر إيقاف الترجمة (الزر المرتبط بالعملية الحالية)
        findViewById<Button>(R.id.btnStopTranslation)?.setOnClickListener {
            val serviceIntent = Intent(this, com.wwm.arabictranslator.services.ScreenTranslateService::class.java)
            stopService(serviceIntent)
        }

        // ربط أزرار التنقل بين الشاشات
        findViewById<Button>(R.id.btnOpenAiSettings)?.setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenGlossary)?.setOnClickListener {
            startActivity(Intent(this, GlossaryActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenTranslationMemory)?.setOnClickListener {
            startActivity(Intent(this, TranslationMemoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenSettings)?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
