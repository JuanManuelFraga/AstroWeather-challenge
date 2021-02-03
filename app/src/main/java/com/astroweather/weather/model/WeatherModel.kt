package com.astroweather.weather.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.android.parcel.Parcelize

@Parcelize
class WeatherModel(
    val title: String,
    val subtitle: String?,
    @ColorRes val primaryColor: Int?,
    val temperature: String,
    val weatherStyle: WeatherStyleModel?,
    val properties: List<WeatherPropertyModel>
): Parcelable