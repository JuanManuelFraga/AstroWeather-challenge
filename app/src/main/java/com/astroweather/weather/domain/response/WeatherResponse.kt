package com.astroweather.weather.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherResponse(
    @SerializedName("name") val name: String,
    @SerializedName("main") val properties: WeatherPropertiesResponse,
    @SerializedName("weather") val descriptions: List<WeatherDescriptionResponse>,
    @SerializedName("wind") val wind: WeatherWindResponse
)