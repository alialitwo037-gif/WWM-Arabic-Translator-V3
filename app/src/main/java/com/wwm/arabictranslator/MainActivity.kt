package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToggleOverlay = findViewById<Button>(R.id.btnToggleOverlay)
        val btnOpenAiSettings = findViewById<Button>(R.id.btnOpenAiSettings)
        val btnOpenGlossary = findViewById<Button>(R.id.btnOpenGlossary)
        val btnOpenTranslationMemory = findViewById<Button>(R.id.btnOpenTranslationMemory)
        val btnOpenOverlaySettings = findViewById<Button>(R.id.btnOpenOverlaySettings)
        val btnOpenSettings = findViewById<Button>(R.id.btnOpenSettings)

        btnToggleOverlay.setOnClickListener {
            // تشغيل/إيقاف خدمة النافذة العائمة
        }

        btnOpenAiSettings.setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        btnOpenGlossary.setOnClickListener {
            startActivity(Intent(this, GlossaryActivity::class.java))
        }

        btnOpenTranslationMemory.setOnClickListener {
            startActivity(Intent(this, TranslationMemoryActivity::class.java))
        }

        btnOpenOverlaySettings.setOnClickListener {
            try {
                val intent = Intent(this, Class.forName("com.wwm.arabictranslator.OverlaySettingsActivity"))
                startActivity(intent)
            } catch (e: ClassNotFoundException) {
                // في حال كانت الأكتيفيتي بنفس الـ package الحالي
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        btnOpenSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
