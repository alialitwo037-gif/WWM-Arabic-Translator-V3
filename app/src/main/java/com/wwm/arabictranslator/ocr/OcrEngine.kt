package com.wwm.arabictranslator.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OcrEngine {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processImage(bitmap: Bitmap, onTextFound: (String) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
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
                // Ignore frame OCR errors silently
            }
    }

    private fun cleanText(rawText: String): String {
        return rawText.lines()
            .map { it.trim() }
            .filter { it.length > 2 }
            .joinToString(" ")
    }
}
