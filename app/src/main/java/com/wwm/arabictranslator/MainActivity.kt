package com.wwm.arabictranslator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService: Button
    private lateinit var btnStopService: Button
    private lateinit var btnPickImage: Button
    private lateinit var btnHistory: Button
    private lateinit var btnSpeak: Button
    private lateinit var etSourceText: EditText
    private lateinit var tvTranslatedText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط عناصر الواجهة
        btnStartService = findViewById(R.id.btnStartService)
        btnStopService = findViewById(R.id.btnStopService)
        btnPickImage = findViewById(R.id.btnPickImage)
        btnHistory = findViewById(R.id.btnHistory)
        btnSpeak = findViewById(R.id.btnSpeak)
        etSourceText = findViewById(R.id.etSourceText)
        tvTranslatedText = findViewById(R.id.tvTranslatedText)

        // إعداد الأزرار والوظائف
        btnStartService.setOnClickListener {
            Toast.makeText(this, "تم تشغيل المترجم العائم", Toast.LENGTH_SHORT).show()
        }

        btnStopService.setOnClickListener {
            Toast.makeText(this, "تم إيقاف المترجم العائم", Toast.LENGTH_SHORT).show()
        }

        btnPickImage.setOnClickListener {
            Toast.makeText(this, "فتح المعرض لاختيار صورة...", Toast.LENGTH_SHORT).show()
        }

        btnHistory.setOnClickListener {
            Toast.makeText(this, "عرض سجل الترجمات المحفوظة...", Toast.LENGTH_SHORT).show()
        }

        btnSpeak.setOnClickListener {
            Toast.makeText(this, "جاري قراءة النص...", Toast.LENGTH_SHORT).show()
        }
    }
}
