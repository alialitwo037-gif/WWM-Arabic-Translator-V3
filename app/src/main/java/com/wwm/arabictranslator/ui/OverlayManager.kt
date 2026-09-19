package com.wwm.arabictranslator.ui

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class OverlayManager(private val context: Context) {

    private var windowManager: WindowManager? = null
    private var containerView: LinearLayout? = null
    private var textView: TextView? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    fun showOverlay() {
        if (containerView != null) return

        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        containerView = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(Color.parseColor("#DD000000"))
            setPadding(30, 20, 30, 20)
            gravity = Gravity.CENTER
        }

        textView = TextView(context).apply {
            text = "WWM Translator Ready..."
            setTextColor(Color.YELLOW)
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 16f)
            textDirection = View.TEXT_DIRECTION_RTL
        }

        containerView?.addView(textView)

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            y = 120
        }

        setupTouchListener()

        try {
            windowManager?.addView(containerView, layoutParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupTouchListener() {
        var initialY = 0
        var initialTouchY = 0f

        containerView?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = layoutParams?.y ?: 0
                    initialTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    layoutParams?.y = initialY - (event.rawY - initialTouchY).toInt()
                    windowManager?.updateViewLayout(containerView, layoutParams)
                    true
                }
                else -> false
            }
        }
    }

    fun updateTranslationText(translatedText: String) {
        textView?.post {
            textView?.text = translatedText
        }
    }

    fun removeOverlay() {
        if (containerView != null && windowManager != null) {
            try {
                windowManager?.removeView(containerView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            containerView = null
        }
    }
}
