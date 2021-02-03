package com.astroweather.util

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Activity.setStatusBarColor(@ColorRes color: Int, isTextDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val lFlags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = if (isTextDark) {
            lFlags.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            lFlags.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        }
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}