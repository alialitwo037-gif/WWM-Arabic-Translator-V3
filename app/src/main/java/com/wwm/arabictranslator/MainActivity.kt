package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wwm.arabictranslator.ai.WwmAiActivity
import com.wwm.arabictranslator.glossary.GlossaryActivity
import com.wwm.arabictranslator.memory.TranslationMemoryActivity
import com.wwm.arabictranslator.service.TranslatorService
import com.wwm.arabictranslator.ui.OverlaySettingsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStartTranslation)
        val btnStop = findViewById<Button>(R.id.btnStopTranslation)

        val btnWwmAi = findViewById<LinearLayout>(R.id.btnWwmAi)
        val btnGlossary = findViewById<LinearLayout>(R.id.btnGlossary)
        val btnMemory = findViewById<LinearLayout>(R.id.btnMemory)
        val btnSettings = findViewById<LinearLayout>(R.id.btnSettings)

        // تشغيل خدمة الترجمة والتراكب
        btnStart.setOnClickListener {
            val intent = Intent(this, TranslatorService::class.java)
            startService(intent)
            Toast.makeText(this, "تم تشغيل خدمة الترجمة", Toast.LENGTH_SHORT).show()
        }

        // إيقاف الخدمة
        btnStop.setOnClickListener {
            val intent = Intent(this, TranslatorService::class.java)
            stopService(intent)
            Toast.makeText(this, "تم إيقاف خدمة الترجمة", Toast.LENGTH_SHORT).show()
        }

        // التنقل إلى شاشة WWM AI
        btnWwmAi.setOnClickListener {
            val intent = Intent(this, WwmAiActivity::class.java)
            startActivity(intent)
        }

        // التنقل إلى شاشة قاموس المصطلحات
        btnGlossary.setOnClickListener {
            val intent = Intent(this, GlossaryActivity::class.java)
            startActivity(intent)
        }

        // التنقل إلى شاشة ذاكرة الترجمة
        btnMemory.setOnClickListener {
            val intent = Intent(this, TranslationMemoryActivity::class.java)
            startActivity(intent)
        }

        // التنقل إلى شاشة إعدادات Overlay
        btnSettings.setOnClickListener {
            val intent = Intent(this, OverlaySettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
