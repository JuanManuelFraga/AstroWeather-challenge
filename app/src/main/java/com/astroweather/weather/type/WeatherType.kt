package com.astroweather.weather.type

import androidx.annotation.RawRes
import com.astroweather.R

enum class WeatherType(val iconId: String, @RawRes val dayLottie: Int, @RawRes val nightLottie: Int) {

    CLEAR_SKY("01", R.raw.clear_sky, R.raw.clear_sky_night),
    FEW_CLOUDS("02", R.raw.few_clouds, R.raw.few_clouds_night),
    SCATTERED_CLOUDS("03", R.raw.scattered_clouds, R.raw.scattered_clouds),
    BROKEN_CLOUDS("04", R.raw.scattered_clouds, R.raw.scattered_clouds),
    SHOWER_RAIN("09", R.raw.rain, R.raw.rain_night),
    RAIN("10", R.raw.rain, R.raw.rain_night),
    THUNDERSTORM("11", R.raw.thunderstorm, R.raw.thunderstorm),
    SNOW("13", R.raw.snow, R.raw.snow),
    MIST("50", R.raw.mist, R.raw.mist);

    companion object {

        fun getTypeByDescription(icon: String): WeatherType? {
            return values().firstOrNull { icon.contains(it.iconId) }
        }
    }
}