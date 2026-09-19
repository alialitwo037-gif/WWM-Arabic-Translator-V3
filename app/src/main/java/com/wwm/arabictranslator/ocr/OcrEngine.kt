package com.wwm.arabictranslator.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OcrEngine {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processImage(bitmap: Bitmap, onTextFound: (String) -> Unit) {
        try {
            // اقتصاص آمن لمنطقة الحوارات أسفل الشاشة
            val startY = (bitmap.height * 0.55).toInt()
            val cropHeight = (bitmap.height * 0.40).toInt()
            val validHeight = if (startY + cropHeight <= bitmap.height) cropHeight else bitmap.height - startY

            val croppedBitmap = if (startY >= 0 && validHeight > 0) {
                Bitmap.createBitmap(bitmap, 0, startY, bitmap.width, validHeight)
            } else {
                bitmap
            }

            val image = InputImage.fromBitmap(croppedBitmap, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectedText = visionText.text.trim()
                    if (detectedText.isNotEmpty()) {
                        val cleanedText = cleanText(detectedText)
                        if (cleanedText.isNotEmpty()) {
                            onTextFound(cleanedText)
                        }
                    }
                }
        } catch (e: Exception) {
            // تجاهل أي خطأ في أبعاد الصورة لمنع الكراش
        }
    }

    private fun cleanText(rawText: String): String {
        return rawText.lines()
            .map { it.trim() }
            .filter { it.length > 2 }
            .joinToString(" ")
    }
}
