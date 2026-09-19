package com.wwm.arabictranslator.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class TranslationEngine {

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.ARABIC)
        .build()

    private val englishArabicTranslator: Translator = Translation.getClient(options)
    private var isModelDownloaded = false

    suspend fun translate(text: String): String {
        return try {
            if (!isModelDownloaded) {
                englishArabicTranslator.downloadModelIfNeeded().await()
                isModelDownloaded = true
            }
            val result = englishArabicTranslator.translate(text).await()
            result.ifEmpty { text }
        } catch (e: Exception) {
            "خطأ في الترجمة: ${e.localizedMessage}"
        }
    }
}
