package com.wwm.arabictranslator.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.wwm.arabictranslator.R
import com.wwm.arabictranslator.settings.TranslatorSettings

class OverlaySettingsActivity : AppCompatActivity() {

    private lateinit var settings: TranslatorSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay_settings)

        settings = TranslatorSettings(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val sbFontSize = findViewById<SeekBar>(R.id.sbFontSize)
        val sbOpacity = findViewById<SeekBar>(R.id.sbOpacity)
        val swShowOriginal = findViewById<SwitchCompat>(R.id.swShowOriginal)
        val btnSave = findViewById<Button>(R.id.btnSaveOverlaySettings)

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            Toast.makeText(this, "تم حفظ إعدادات الـ Overlay بنجاح", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
