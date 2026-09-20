package com.wwm.arabictranslator

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OverlaySettingsActivity : AppCompatActivity() {

    private lateinit var seekBarFontSize: SeekBar
    private lateinit var seekBarOpacity: SeekBar
    private lateinit var switchShowOriginal: Switch
    private lateinit var btnSaveOverlay: Button

    companion object {
        const val PREF_OVERLAY = "WwmOverlayPrefs"
        const val KEY_FONT_SIZE = "font_size"
        const val KEY_OPACITY = "opacity"
        const val KEY_SHOW_ORIGINAL = "show_original"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay_settings)

        seekBarFontSize = findViewById(R.id.seekBarFontSize)
        seekBarOpacity = findViewById(R.id.seekBarOpacity)
        switchShowOriginal = findViewById(R.id.switchShowOriginal)
        btnSaveOverlay = findViewById(R.id.btnSaveOverlay)

        loadOverlaySettings()

        btnSaveOverlay.setOnClickListener {
            saveOverlaySettings()
        }
    }

    private fun loadOverlaySettings() {
        val prefs = getSharedPreferences(PREF_OVERLAY, Context.MODE_PRIVATE)
        val fontSize = prefs.getInt(KEY_FONT_SIZE, 18)
        val opacity = prefs.getInt(KEY_OPACITY, 70)
        val showOriginal = prefs.getBoolean(KEY_SHOW_ORIGINAL, true)

        seekBarFontSize.progress = fontSize
        seekBarOpacity.progress = opacity
        switchShowOriginal.isChecked = showOriginal
    }

    private fun saveOverlaySettings() {
        val prefs = getSharedPreferences(PREF_OVERLAY, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        
        editor.putInt(KEY_FONT_SIZE, seekBarFontSize.progress)
        editor.putInt(KEY_OPACITY, seekBarOpacity.progress)
        editor.putBoolean(KEY_SHOW_ORIGINAL, switchShowOriginal.isChecked)
        editor.apply()

        Toast.makeText(this, "تم حفظ إعدادات الـ Overlay بنجاح", Toast.LENGTH_SHORT).show()
        finish()
    }
}
