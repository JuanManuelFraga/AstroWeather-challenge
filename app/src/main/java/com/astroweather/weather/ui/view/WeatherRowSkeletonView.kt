package com.astroweather.weather.ui.view

import android.content.Context
import android.util.AttributeSet
import com.astroweather.R
import com.facebook.shimmer.ShimmerFrameLayout

class WeatherRowSkeletonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.base_row_skeleton, this)
        startShimmer()
    }
}