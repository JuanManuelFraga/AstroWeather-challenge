package com.astroweather.weather.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
class WeatherPropertyModel(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val description: String
): Parcelable