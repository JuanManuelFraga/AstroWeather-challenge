package com.astroweather.weather.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherWindResponse(
    @SerializedName("speed") val speed: Double
)