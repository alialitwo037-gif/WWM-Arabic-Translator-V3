package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnAiSettings: Button
    private lateinit var btnPreferencesHelper: Button
    private lateinit var btnTranslationInterface: Button
    private lateinit var btnOverlaySettings: Button
    private lateinit var btnTranslationMemory: Button
    private lateinit var btnGeneralSettings: Button
    private lateinit var tvStatusInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط الأزرار الستة بالـ XML
        btnAiSettings = findViewById(R.id.btnAiSettings)
        btnPreferencesHelper = findViewById(R.id.btnPreferencesHelper)
        btnTranslationInterface = findViewById(R.id.btnTranslationInterface)
        btnOverlaySettings = findViewById(R.id.btnOverlaySettings)
        btnTranslationMemory = findViewById(R.id.btnTranslationMemory)
        btnGeneralSettings = findViewById(R.id.btnGeneralSettings)
        tvStatusInfo = findViewById(R.id.tvStatusInfo)

        // 1. زر إعدادات المساعد والذكاء الاصطناعي
        btnAiSettings.setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        // 2. زر إدارة الذاكرة ومفاتيح الربط
        btnPreferencesHelper.setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        // 3. زر واجهة الترجمة المتقدمة
        btnTranslationInterface.setOnClickListener {
            startActivity(Intent(this, WwmAiActivity::class.java))
        }

        // 4. زر إعدادات الـ Overlay والشاشة
        btnOverlaySettings.setOnClickListener {
            startActivity(Intent(this, OverlaySettingsActivity::class.java))
        }

        // 5. زر ذاكرة الترجمة والمصطلحات
        btnTranslationMemory.setOnClickListener {
            startActivity(Intent(this, TranslationMemoryActivity::class.java))
        }

        // 6. زر الإعدادات العامة الشاملة
        btnGeneralSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
