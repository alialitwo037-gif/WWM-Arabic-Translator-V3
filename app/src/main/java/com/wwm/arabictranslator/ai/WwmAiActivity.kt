package com.wwm.arabictranslator.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wwm.arabictranslator.R

class WwmAiActivity : AppCompatActivity() {

    private lateinit var aiEngine: WwmAiEngine
    private lateinit var tvAiResponse: TextView
    private lateinit var etAiQuery: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wwm_ai)

        aiEngine = WwmAiEngine(this)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        etAiQuery = findViewById(R.id.etAiQuery)
        val btnAskAi = findViewById<Button>(R.id.btnAskAi)
        tvAiResponse = findViewById(R.id.tvAiResponse)

        val btnQuickStory = findViewById<Button>(R.id.btnQuickStory)
        val btnQuickTask = findViewById<Button>(R.id.btnQuickTask)

        btnBack.setOnClickListener {
            finish()
        }

        btnAskAi.setOnClickListener {
            val query = etAiQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                sendToAi("سؤال حول اللعبة: $query")
            } else {
                Toast.makeText(this, "يرجى كتابة سؤال أولاً", Toast.LENGTH_SHORT).show()
            }
        }

        btnQuickStory.setOnClickListener {
            sendToAi("اشرح لي قصة المهمة أو المشهد الحالي باختصار.")
        }

        btnQuickTask.setOnClickListener {
            sendToAi("ما المطلوب مني فعله بالضبط في هذه المهمة؟")
        }
    }

    private fun sendToAi(prompt: String) {
        tvAiResponse.text = "جاري التفكير والإجابة..."
        // استدعاء محرك الذكاء الاصطناعي
        aiEngine.processPrompt(prompt) { result ->
            runOnUiThread {
                tvAiResponse.text = result
            }
        }
    }
}
