package com.astroweather.weather.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherPropertiesResponse(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_min") val minTemperature: Double,
    @SerializedName("temp_max") val maxTemperature: Double,
)