package com.wwm.arabictranslator.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OcrEngine {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processImage(bitmap: Bitmap, onTextFound: (String) -> Unit) {
        try {
            // اقتطاع منطقة الحوارات أسفل الشاشة (بين 60% إلى 95% من ارتفاع الشاشة)
            val startY = (bitmap.height * 0.60).toInt()
            val cropHeight = (bitmap.height * 0.35).toInt()

            val croppedBitmap = if (startY + cropHeight <= bitmap.height) {
                Bitmap.createBitmap(bitmap, 0, startY, bitmap.width, cropHeight)
            } else {
                bitmap
            }

            val image = InputImage.fromBitmap(croppedBitmap, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val rawText = visionText.text
                    val cleaned = cleanText(rawText)
                    if (cleaned.isNotEmpty()) {
                        onTextFound(cleaned)
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cleanText(rawText: String): String {
        return rawText.lines()
            .map { it.trim() }
            .filter { line -> line.length > 2 && line.any { it.isLetter() } }
            .joinToString(" ")
    }
}
