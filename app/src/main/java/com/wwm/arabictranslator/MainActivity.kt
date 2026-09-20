package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
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

        // ربط وظيفة زر بدء الترجمة (الزر رقم 1) بالخدمة الصحيحة TranslationService
        findViewById<Button>(R.id.btnStartTranslation)?.setOnClickListener {
            if (!android.provider.Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "يرجى منح إذن العرض فوق التطبيقات لتمكين الترجمة", Toast.LENGTH_LONG).show()
                val intent = Intent(
                    android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    android.net.Uri.parse("package:$packageName")
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "جاري بدء الترجمة...", Toast.LENGTH_SHORT).show()
                val serviceIntent = Intent(this, TranslationService::class.java)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                } else {
                    startService(serviceIntent)
                }
            }
        }

        // ربط وظيفة زر إيقاف الترجمة بالخدمة الصحيحة
        findViewById<Button>(R.id.btnStopTranslation)?.setOnClickListener {
            Toast.makeText(this, "تم إيقاف الترجمة", Toast.LENGTH_SHORT).show()
            val serviceIntent = Intent(this, TranslationService::class.java)
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
