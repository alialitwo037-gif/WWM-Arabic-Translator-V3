package com.wwm.arabictranslator

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class GlossaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glossary)

        val etSearchGlossary = findViewById<EditText>(R.id.etSearchGlossary)
    }
}
