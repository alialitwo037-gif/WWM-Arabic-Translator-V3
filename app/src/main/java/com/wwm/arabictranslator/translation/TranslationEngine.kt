package com.wwm.arabictranslator.translation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class TranslationEngine {

    // قاموس مصطلحات لعبة Where Winds Meet المخصص
    private val wwmGlossary = mapOf(
        "Where Winds Meet" to "حيث تلتقي الرياح",
        "Wuxia" to "ووكسيا",
        "Jianghu" to "جيانغ هو",
        "Sect" to "طائفة",
        "Martial Arts" to "الفنون القتالية",
        "Inner Power" to "الطاقة الداخلية (كاي)",
        "Lightness Skill" to "مهارة الخفة (التحليق)",
        "Qinggong" to "شينغ غونغ",
        "Swordsman" to "سياف"
    )

    suspend fun translate(text: String, sourceLang: String = "en", targetLang: String = "ar"): String {
        return withContext(Dispatchers.IO) {
            try {
                // 1. تطبيق القاموس المخصص قبل الإرسال لتصحيح المصطلحات
                var processedText = text
                wwmGlossary.forEach { (enTerm, arTerm) ->
                    if (processedText.contains(enTerm, ignoreCase = true)) {
                        processedText = processedText.replace(Regex("(?i)\\b$enTerm\\b"), arTerm)
                    }
                }

                // إذا أصبح النص كاملاً معرباً عن طريق القاموس المخصص، نعيده مباشرة
                if (!containsEnglish(processedText)) {
                    return@withContext processedText
                }

                // 2. الترجمة عبر المحرك
                val encodedText = URLEncoder.encode(processedText, "UTF-8")
                val urlString = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=$sourceLang&tl=$targetLang&dt=t&q=$encodedText"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 4000
                connection.readTimeout = 4000

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(response)
                val sentences = jsonArray.getJSONArray(0)

                val translatedBuilder = StringBuilder()
                for (i in 0 until sentences.length()) {
                    val sentence = sentences.getJSONArray(i)
                    translatedBuilder.append(sentence.getString(0))
                }
                
                translatedBuilder.toString()
            } catch (e: Exception) {
                text
            }
        }
    }

    private fun containsEnglish(text: String): Boolean {
        return text.matches(Regex(".*[a-zA-Z]+.*"))
    }
}
