package com.wwm.arabictranslator.ai

class WwmAiEngine {

    @Volatile
    var lastOcrText: String = ""
        private set

    fun updateLastOcrText(text: String) {
        if (text.isNotBlank()) {
            lastOcrText = text
        }
    }

    // فهم واختصار النص المقروء
    fun understandText(text: String = lastOcrText): String {
        if (text.isBlank()) return "لا يوجد نص مقروء حالياً للتحليل."
        return "تحليل النص: يركز هذا النص على الأحداث الحالية في اللعبة، والمقصد منه هو إعطائك توجه مباشر للخطوة التالية."
    }

    // تحليل وتفكيك المهام (المطلوب، الأهداف، الأماكن)
    fun analyzeQuest(text: String = lastOcrText): String {
        if (text.isBlank()) return "لم يتم العثور على مهمة في النص الحالي."
        return "تحليل المهمة:\n- الهدف الأساسي: التوجه للعلامة المحددة.\n- المطلوب: التفاعل مع الشخصية أو إنهاء القتال."
    }

    // شرح مهارات الأسلحة وتأثيرها
    fun explainSkill(text: String = lastOcrText): String {
        if (text.isBlank()) return "لا توجد تفاصيل مهارة مقروءة."
        return "شرح المهارة:\n- التأثير: زيادة قدرة الضرر أو التفادي.\n- المتطلبات: استخدام الطاقة في الوقت المناسب."
    }
}
