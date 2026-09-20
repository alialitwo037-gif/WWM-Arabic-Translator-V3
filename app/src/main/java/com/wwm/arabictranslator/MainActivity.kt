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

        // تطبيق التدرجات والأشكال الذكية برمجياً
        val headerCard = findViewById<LinearLayout>(R.id.headerCardContainer)
        if (headerCard != null) {
            UiGenerator.applyHeaderGradient(headerCard)
        }

        // ربط أزرار التنقل بقية الشاشات
        findViewById<Button>(R.id.btnOpenAiSettings).setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenGlossary).setOnClickListener {
            startActivity(Intent(this, GlossaryActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenTranslationMemory).setOnClickListener {
            startActivity(Intent(this, TranslationMemoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnOpenSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
