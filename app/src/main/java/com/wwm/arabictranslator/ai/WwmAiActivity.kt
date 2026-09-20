package com.wwm.arabictranslator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class WwmAiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wwm_ai)

        val spAiEngine = findViewById<Spinner>(R.id.spAiEngine)
        val etApiKey = findViewById<EditText>(R.id.etApiKey)
        val btnSaveAiConfig = findViewById<Button>(R.id.btnSaveAiConfig)

        btnSaveAiConfig.setOnClickListener {
            finish()
        }
    }
}
