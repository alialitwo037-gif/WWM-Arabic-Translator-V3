package com.wwm.arabictranslator

import android.content.Context

object WwmPreferencesHelper {
    private const val PREF_NAME = "WwmAiPrefs"
    private const val KEY_API_KEY = "api_key"
    private const val KEY_SELECTED_MODEL = "selected_model"

    // دالة حقيقية وعميقة لجلب مفتاح الـ API المحفوظ للاستخدام الفعلي في الترجمة
    fun getApiKey(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_API_KEY, "") ?: ""
    }

    // دالة حقيقية لجلب النموذج المختار فعلياً من قبل المستخدم
    fun getSelectedModel(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_SELECTED_MODEL, "Gemini Pro (موصى به للألعاب)") ?: "Gemini Pro (موصى به للألعاب)"
    }
}
