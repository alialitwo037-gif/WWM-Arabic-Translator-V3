package com.wwm.arabictranslator.utils

import android.graphics.drawable.GradientDrawable
import android.view.View

object UiGenerator {

    // توليد خلفية متدرجة ذكية للرأس (Gradient Header)
    fun applyHeaderGradient(view: View) {
        val drawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xFF1E1B4B.toInt(), 0xFF312E81.toInt())
        )
        drawable.cornerRadius = dpToPx(view, 16f)
        view.background = drawable
    }

    // توليد خلفية البطاقات والعناصر بأسلوب ذكي
    fun applyCardBackground(view: View) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(0xFF1E293B.toInt()) // لون الخلفية الداكن
        drawable.cornerRadius = dpToPx(view, 12f)
        drawable.setStroke(dpToPxInt(view, 1f), 0xFF334155.toInt()) // لون الإطار
        view.background = drawable
    }

    private fun dpToPx(view: View, dp: Float): Float {
        return dp * view.resources.displayMetrics.density
    }

    private fun dpToPxInt(view: View, dp: Float): Int {
        return (dpToPx(view, dp)).toInt()
    }
}
