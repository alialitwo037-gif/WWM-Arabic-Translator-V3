package com.wwm.arabictranslator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this).apply {
            text = "WWM Arabic Translator - Base Ready"
            textSize = 20f
            setPadding(50, 50, 50, 50)
        }
        setContentView(textView)
    }
}
