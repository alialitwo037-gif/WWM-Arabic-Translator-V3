package com.wwm.arabictranslator.glossary

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class GlossaryManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("wwm_glossary", Context.MODE_PRIVATE)
    private val glossaryMap = HashMap<String, String>()

    init {
        loadGlossary()
        if (glossaryMap.isEmpty()) {
            // إضافة مصطلحات أفتراضية للعبة WWM
            addTerm("Where Winds Meet", "حيث تلتقي الرياح")
            addTerm("Sword", "سيف")
            addTerm("Martial Arts", "الفنون القتالية")
        }
    }

    fun addTerm(english: String, arabic: String) {
        glossaryMap[english.lowercase().trim()] = arabic
        saveGlossary()
    }

    fun removeTerm(english: String) {
        glossaryMap.remove(english.lowercase().trim())
        saveGlossary()
    }

    fun applyGlossary(text: String): String {
        var result = text
        // ترتيب المصطلحات حسب الطول تنازلياً لتجنب الاستبدال الخاطئ للأجزاء
        val sortedKeys = glossaryMap.keys.sortedByDescending { it.length }
        for (key in sortedKeys) {
            val value = glossaryMap[key] ?: continue
            val regex = Regex("(?i)\\b${Regex.escape(key)}\\b")
            result = regex.replace(result, value)
        }
        return result
    }

    private fun saveGlossary() {
        val json = JSONObject(glossaryMap as Map<*, *>).toString()
        prefs.edit().putString("terms_json", json).apply()
    }

    private fun loadGlossary() {
        val jsonString = prefs.getString("terms_json", null) ?: return
        try {
            val json = JSONObject(jsonString)
            val keys = json.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                glossaryMap[key] = json.getString(key)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
