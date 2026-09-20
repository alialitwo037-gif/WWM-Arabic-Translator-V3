package com.wwm.arabictranslator.settings

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.wwm.arabictranslator.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var translatorSettings: TranslatorSettings
    private lateinit var etApiKey: EditText
    private lateinit var swAutoOcr: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        translatorSettings = TranslatorSettings(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        etApiKey = findViewById(R.id.etApiKey)
        swAutoOcr = findViewById(R.id.swAutoOcr)
        val btnSave = findViewById<Button>(R.id.btnSaveSettings)

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val key = etApiKey.text.toString().trim()
            translatorSettings.saveApiKey(key)
            Toast.makeText(this, "تم حفظ الإعدادات بنجاح", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
