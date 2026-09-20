package com.wwm.arabictranslator.memory

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wwm.arabictranslator.R

class TranslationMemoryActivity : AppCompatActivity() {

    private lateinit var translationMemory: TranslationMemory
    private lateinit var etSearchMemory: EditText
    private lateinit var tvMemoryCountHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_memory)

        translationMemory = TranslationMemory(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        etSearchMemory = findViewById(R.id.etSearchMemory)
        tvMemoryCountHeader = findViewById(R.id.tvMemoryCountHeader)

        val btnExportJson = findViewById<Button>(R.id.btnExportJson)
        val btnImportJson = findViewById<Button>(R.id.btnImportJson)
        val btnClearMemory = findViewById<Button>(R.id.btnClearMemory)

        btnBack.setOnClickListener {
            finish()
        }

        btnExportJson.setOnClickListener {
            Toast.makeText(this, "تصدير ذاكرة الترجمة إلى JSON", Toast.LENGTH_SHORT).show()
        }

        btnImportJson.setOnClickListener {
            Toast.makeText(this, "استيراد ذاكرة الترجمة من JSON", Toast.LENGTH_SHORT).show()
        }

        btnClearMemory.setOnClickListener {
            Toast.makeText(this, "تم مسح الذاكرة", Toast.LENGTH_SHORT).show()
        }
    }
}
