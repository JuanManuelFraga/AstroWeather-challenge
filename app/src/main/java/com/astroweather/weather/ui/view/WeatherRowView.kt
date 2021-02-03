package com.astroweather.weather.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.astroweather.R
import com.astroweather.weather.model.WeatherPropertyModel
import kotlinx.android.synthetic.main.weather_row_layout.view.*

class WeatherRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.weather_row_layout, this)
        val paddingTopBottom = resources.getDimensionPixelSize(R.dimen._12sdp)
        setPadding(0, paddingTopBottom, 0, paddingTopBottom)
    }

    fun bind(rowModel: WeatherPropertyModel) {
        rowTitle.text = resources.getString(rowModel.title)
        rowIcon.setImageResource(rowModel.icon)
        rowDescription.text = rowModel.description
    }
}