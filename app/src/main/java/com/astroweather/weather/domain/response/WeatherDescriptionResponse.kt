package com.astroweather.weather.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDescriptionResponse(
    @SerializedName("description") val state: String,
    @SerializedName("icon") val icon: String
)