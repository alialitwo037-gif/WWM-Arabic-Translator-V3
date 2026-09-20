package com.wwm.arabictranslator

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object WwmGeminiService {

    suspend fun translateText(context: Context, textToTranslate: String): String = withContext(Dispatchers.IO) {
        val apiKey = WwmPreferencesHelper.getApiKey(context)
        if (apiKey.isEmpty()) {
            return@withContext "خطأ: يرجى إدخال مفتاح الـ API في إعدادات المساعد أولاً."
        }

        // استخدام نموذج Gemini الحديث
        val urlString = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
        
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.doOutput = true

            // صياغة الطلب للذكاء الاصطناعي لترجمة ألعاب العالم المفتوح
            val prompt = "Translate the following game text to professional Arabic, suitable for open-world video games context: \"$textToTranslate\""
            
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().put(
                    JSONObject().put("parts", JSONArray().put(
                        JSONObject().put("text", prompt)
                    ))
                ))
            }

            connection.outputStream.use { os ->
                os.write(jsonBody.toString().toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val responseString = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(responseString)
                val candidates = jsonResponse.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val content = candidates.getJSONObject(0).getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text").trim()
                    }
                }
                return@withContext "لم يتم العثور على ترجمة."
            } else {
                return@withContext "خطأ في الاتصال بالخادم: الرمز $responseCode"
            }
        } catch (e: Exception) {
            return@withContext "حدث خطأ أثناء الترجمة: ${e.localizedMessage}"
        }
    }
}
