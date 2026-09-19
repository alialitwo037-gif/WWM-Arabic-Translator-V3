package com.wwm.arabictranslator.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

class TranslatorSettings(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("wwm_translator_settings", Context.MODE_PRIVATE)

    var textSize: Float
        get() = prefs.getFloat("text_size", 16f)
        set(value) = prefs.edit().putFloat("text_size", value).apply()

    var textColor: Int
        get() = prefs.getInt("text_color", Color.YELLOW)
        set(value) = prefs.edit().putInt("text_color", value).apply()

    var backgroundColor: Int
        get() = prefs.getInt("bg_color", Color.parseColor("#DD000000"))
        set(value) = prefs.edit().putInt("bg_color", value).apply()

    var isShowOriginalText: Boolean
        get() = prefs.getBoolean("show_original", false)
        set(value) = prefs.edit().putBoolean("show_original", value).apply()

    var captureInterval: Long
        get() = prefs.getLong("capture_interval", 1200L)
        set(value) = prefs.edit().putLong("capture_interval", value).apply()
}
