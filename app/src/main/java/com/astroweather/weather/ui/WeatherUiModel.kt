package com.astroweather.weather.ui

import android.os.Parcelable
import androidx.annotation.StringRes
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.weather.domain.response.WeatherResponse
import com.astroweather.weather.model.WeatherModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherUiModel(
    val showLoading: Boolean,
    val showSkeleton: Boolean,
    val showWeather: WeatherModel?,
    val showLocationInfo: ChooserModel?,
    @StringRes val showErrorMessage: Int?
) : Parcelable