package com.wwm.arabictranslator.ui

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.wwm.arabictranslator.R

class OverlayManager(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: View? = null
    private var tvTranslation: TextView? = null

    fun showOverlay() {
        if (overlayView != null) return

        val layoutParamsType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutParamsType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            y = 150
        }

        val tv = TextView(context).apply {
            text = "WWM Translator Ready..."
            setTextColor(Color.YELLOW)
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 18f)
            setBackgroundColor(Color.parseColor("#80000000"))
            setPadding(32, 16, 32, 16)
            textDirection = View.TEXT_DIRECTION_RTL
            gravity = Gravity.CENTER
        }

        tvTranslation = tv
        overlayView = tv
        windowManager.addView(overlayView, params)
    }

    fun updateTranslationText(text: String) {
        tvTranslation?.post {
            tvTranslation?.text = text
        }
    }

    fun removeOverlay() {
        overlayView?.let {
            windowManager.removeView(it)
            overlayView = null
            tvTranslation = null
        }
    }
}
