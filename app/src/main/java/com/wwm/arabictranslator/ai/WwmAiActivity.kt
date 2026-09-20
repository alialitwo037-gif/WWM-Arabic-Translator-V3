package com.wwm.arabictranslator

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WwmAiActivity : AppCompatActivity() {

    private lateinit var etApiKey: EditText
    private lateinit var spinnerAiModel: Spinner
    private lateinit var btnSaveAiSettings: Button

    companion object {
        const val PREF_NAME = "WwmAiPrefs"
        const val KEY_API_KEY = "api_key"
        const val KEY_SELECTED_MODEL = "selected_model"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wwm_ai)

        etApiKey = findViewById(R.id.etApiKey)
        spinnerAiModel = findViewById(R.id.spinnerAiModel)
        btnSaveAiSettings = findViewById(R.id.btnSaveAiSettings)

        // خيارات النماذج المدعومة
        val models = arrayOf("Gemini Pro (موصى به للألعاب)", "GPT-4o Mini", "Claude 3.5 Sonnet")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, models)
        spinnerAiModel.adapter = adapter

        // جلب البيانات المحفوظة مسبقاً لعرض الحالة الحقيقية للمستخدم
        loadSavedSettings(models)

        // تفعيل زر الحفظ الوظيفي العميق والدائم
        btnSaveAiSettings.setOnClickListener {
            val apiKey = etApiKey.text.toString().trim()
            val selectedModel = spinnerAiModel.selectedItem.toString()

            if (apiKey.isEmpty()) {
                Toast.makeText(this, "يرجى إدخال مفتاح الـ API أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString(KEY_API_KEY, apiKey)
                putString(KEY_SELECTED_MODEL, selectedModel)
                apply()
            }

            Toast.makeText(this, "تم حفظ إعدادات الذكاء الاصطناعي بنجاح!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun loadSavedSettings(models: Array<String>) {
        val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedApiKey = sharedPreferences.getString(KEY_API_KEY, "")
        val savedModel = sharedPreferences.getString(KEY_SELECTED_MODEL, "")

        if (!savedApiKey.isNullOrEmpty()) {
            etApiKey.setText(savedApiKey)
        }

        if (!savedModel.isNullOrEmpty()) {
            val spinnerPosition = models.indexOf(savedModel)
            if (spinnerPosition >= 0) {
                spinnerAiModel.setSelection(spinnerPosition)
            }
        }
    }
}
