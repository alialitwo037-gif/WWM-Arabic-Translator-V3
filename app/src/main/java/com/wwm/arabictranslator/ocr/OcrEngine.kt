package com.wwm.arabictranslator.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OcrEngine {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processImage(bitmap: Bitmap, onTextFound: (String) -> Unit) {
        // اقتصاص الجزء السفلي من الشاشة (منطقة الحوارات والنصوص الرئيسية)
        // يبدأ من 60% من ارتفاع الشاشة حتى 95%
        val startY = (bitmap.height * 0.60).toInt()
        val cropHeight = (bitmap.height * 0.35).toInt()

        val croppedBitmap = try {
            Bitmap.createBitmap(bitmap, 0, startY, bitmap.width, cropHeight)
        } catch (e: Exception) {
            bitmap // fallback إذا حدث خطأ في الأبعاد
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
            .addOnFailureListener {
                // Ignore frame OCR errors
            }
    }

    private fun cleanText(rawText: String): String {
        return rawText.lines()
            .map { it.trim() }
            .filter { it.length > 2 }
            .joinToString(" ")
    }
}
