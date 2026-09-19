package com.wwm.arabictranslator.memory

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class TranslationMemory(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("wwm_translation_memory", Context.MODE_PRIVATE)
    private val memoryMap = HashMap<String, String>()

    init {
        loadMemory()
    }

    fun get(text: String): String? {
        return memoryMap[text.trim().lowercase()]
    }

    fun put(original: String, translated: String) {
        val key = original.trim().lowercase()
        if (key.isNotEmpty() && translated.isNotEmpty()) {
            memoryMap[key] = translated
            saveMemory()
        }
    }

    fun clear() {
        memoryMap.clear()
        prefs.edit().clear().apply()
    }

    fun getCount(): Int = memoryMap.size

    private fun saveMemory() {
        val json = JSONObject(memoryMap as Map<*, *>).toString()
        prefs.edit().putString("memory_json", json).apply()
    }

    private fun loadMemory() {
        val jsonString = prefs.getString("memory_json", null) ?: return
        try {
            val json = JSONObject(jsonString)
            val keys = json.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                memoryMap[key] = json.getString(key)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
