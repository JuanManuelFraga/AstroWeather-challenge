package com.astroweather.weather.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import kotlinx.android.parcel.Parcelize

@Parcelize
class WeatherStyleModel(
    @RawRes val lottie: Int,
    @DrawableRes val background: Int,
): Parcelable