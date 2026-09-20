package com.wwm.arabictranslator.glossary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wwm.arabictranslator.R

class GlossaryActivity : AppCompatActivity() {

    private lateinit var glossaryManager: GlossaryManager
    private lateinit var etSearchGlossary: EditText
    private lateinit var tvCountHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glossary)

        glossaryManager = GlossaryManager(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        etSearchGlossary = findViewById(R.id.etSearchGlossary)
        tvCountHeader = findViewById(R.id.tvCountHeader)

        val btnAddTerm = findViewById<Button>(R.id.btnAddTerm)
        val btnExportImport = findViewById<Button>(R.id.btnExportImport)

        btnBack.setOnClickListener {
            finish()
        }

        btnAddTerm.setOnClickListener {
            Toast.makeText(this, "إضافة مصطلح جديد للقاموس", Toast.LENGTH_SHORT).show()
        }

        btnExportImport.setOnClickListener {
            Toast.makeText(this, "استيراد / تصدير ملفات القاموس JSON", Toast.LENGTH_SHORT).show()
        }
    }
}
