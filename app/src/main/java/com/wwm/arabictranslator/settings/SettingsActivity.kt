package com.wwm.arabictranslator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var btnTranslatingSettings: Button
    private lateinit var btnOverlaySettingsMenu: Button
    private lateinit var btnDataSettings: Button
    private lateinit var btnAboutApp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnTranslatingSettings = findViewById(R.id.btnTranslatingSettings)
        btnOverlaySettingsMenu = findViewById(R.id.btnOverlaySettingsMenu)
        btnDataSettings = findViewById(R.id.btnDataSettings)
        btnAboutApp = findViewById(R.id.btnAboutApp)

        btnOverlaySettingsMenu.setOnClickListener {
            val intent = Intent(this, OverlaySettingsActivity::class.java)
            startActivity(intent)
        }

        btnTranslatingSettings.setOnClickListener {
            val intent = Intent(this, WwmAiActivity::class.java)
            startActivity(intent)
        }

        btnDataSettings.setOnClickListener {
            val intent = Intent(this, TranslationMemoryActivity::class.java)
            startActivity(intent)
        }

        btnAboutApp.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setTitle("حول التطبيق")
                .setMessage("Wwm Arabic Translator V3\nمخصص لترجمة ألعاب العالم المفتوح بدقة عالية.\nجميع الحقوق محفوظة.")
                .setPositiveButton("حسناً", null)
                .show()
        }
    }
}
