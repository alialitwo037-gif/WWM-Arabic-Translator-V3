package com.example.wwmtranslator

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isServiceActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToggleService = findViewById<Button>(R.id.btnToggleService)
        val tvServiceStatus = findViewById<TextView>(R.id.tvServiceStatus)

        btnToggleService.setOnClickListener {
            isServiceActive = !isServiceActive

            if (isServiceActive) {
                tvServiceStatus.text = "جاهز العمل"
                tvServiceStatus.setTextColor(Color.parseColor("#4CAF50"))
                btnToggleService.text = "إيقاف الترجمة"
                btnToggleService.setBackgroundColor(Color.parseColor("#2A68C5"))
            } else {
                tvServiceStatus.text = "متوقف"
                tvServiceStatus.setTextColor(Color.parseColor("#FF5252"))
                btnToggleService.text = "بدء الترجمة"
                btnToggleService.setBackgroundColor(Color.parseColor("#424242"))
            }
        }
    }
}
