package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        // ----------------------------------------------------
        // [مقطع مؤقت] فاحص الأخطاء الشامل للتجربة (احذفه لاحقاً)
        try {
            Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
                WwmErrorLogger.logError(this, "FatalCrash", "حدث خطأ مفاجئ أدى لتوقف التطبيق", throwable)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(1)
            }

            val allErrors = WwmErrorLogger.readAllErrors(this)
            if (allErrors.isNotEmpty() && !allErrors.contains("لا توجد أخطاء مسجلة")) {
                Toast.zIndexShowOrLog?.let {} // تجنب أي مشاكل صياغة
                Toast.makeText(this, "توجد أخطاء مسجلة في السجل المحلي", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            // تجاهل أخطاء الفاحص المؤقت
        }
        // ----------------------------------------------------

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
