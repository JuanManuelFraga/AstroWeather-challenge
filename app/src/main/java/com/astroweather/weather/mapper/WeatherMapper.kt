package com.astroweather.weather.mapper

import com.astroweather.R
import com.astroweather.weather.domain.response.WeatherDescriptionResponse
import com.astroweather.weather.domain.response.WeatherResponse
import com.astroweather.weather.model.WeatherModel
import com.astroweather.weather.model.WeatherPropertyModel
import com.astroweather.weather.model.WeatherStyleModel
import com.astroweather.weather.type.WeatherType
import com.astroweather.weather.usecase.IsDayIconUseCase
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherMapper @Inject constructor(
    private val isADayIcon: IsDayIconUseCase
) {

    fun map(weatherResponse: WeatherResponse): WeatherModel {
        return WeatherModel(
            title = weatherResponse.name,
            subtitle = getSubtitle(weatherResponse),
            weatherStyle = getWeatherStyle(weatherResponse),
            primaryColor = getPrimaryColor(weatherResponse),
            temperature = parseKelvinToCelsius(weatherResponse.properties.temperature),
            properties = getProperties(weatherResponse)
        )
    }

    private fun getSubtitle(weatherResponse: WeatherResponse): String? {
        return getDescription(weatherResponse)?.state
    }

    private fun getWeatherStyle(weatherResponse: WeatherResponse): WeatherStyleModel? {
        return getDescription(weatherResponse)?.let { description ->
            WeatherType.getTypeByDescription(description.icon)?.let {
                WeatherStyleModel(
                    background = if (isADayIcon(description.icon)) R.drawable.day_background else R.drawable.night_background,
                    lottie = if (isADayIcon(description.icon)) it.dayLottie else it.nightLottie
                )
            }
        }
    }

    private fun getPrimaryColor(weatherResponse: WeatherResponse): Int? {
        return getDescription(weatherResponse)?.let { if (isADayIcon(it.icon)) R.color.blue else R.color.darkBlue }
    }

    private fun parseKelvinToCelsius(temperatureFahrenheit: Double): String {
        val celsius = temperatureFahrenheit - ZERO_DEGREES_IN_KELVIN
        return celsius.roundToInt().toString()
    }

    private fun getDescription(weatherResponse: WeatherResponse): WeatherDescriptionResponse? {
        if (weatherResponse.descriptions.isEmpty()) {
            return null
        }
        return weatherResponse.descriptions[0]
    }

    private fun getProperties(weatherResponse: WeatherResponse) = listOf(
        WeatherPropertyModel(R.string.temperature, R.drawable.temperature_icon, getMaxAndMin(weatherResponse)),
        WeatherPropertyModel(R.string.wind, R.drawable.wind_icon, parseMetersPerSecToMPH(weatherResponse.wind.speed)),
        WeatherPropertyModel(R.string.humidity, R.drawable.humidity_icon, getHumidity(weatherResponse))
    )

    private fun getHumidity(weatherResponse: WeatherResponse): String {
        return weatherResponse.properties.humidity.toString() + " %"
    }

    private fun getMaxAndMin(weatherResponse: WeatherResponse): String {
        val minInCelsius = parseKelvinToCelsius(weatherResponse.properties.minTemperature)
        val maxInCelsius = parseKelvinToCelsius(weatherResponse.properties.maxTemperature)
        return "$minInCelsius / $maxInCelsius °C"
    }

    private fun parseMetersPerSecToMPH(windSpeedInMetersPerSec: Double): String {
        return (ONE_METER_PER_SECOND_IN_MPH * windSpeedInMetersPerSec).roundToInt().toString() + " MPH"
    }

    companion object {
        private const val ZERO_DEGREES_IN_KELVIN = 273.15
        private const val ONE_METER_PER_SECOND_IN_MPH = 2.23694
    }
}