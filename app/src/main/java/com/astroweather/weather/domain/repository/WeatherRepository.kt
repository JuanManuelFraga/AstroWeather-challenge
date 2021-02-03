package com.astroweather.weather.domain.repository

import com.astroweather.R
import com.astroweather.weather.domain.response.WeatherResponse
import com.astroweather.weather.domain.service.WeatherService
import com.astroweather.weather.usecase.GetUserLocationUseCase
import com.astroweather.common.retrofit.PerformRequestUseCase
import com.astroweather.util.Result
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val getUserLocation: GetUserLocationUseCase,
    private val performRequest: PerformRequestUseCase,
    private val weatherService: WeatherService
) {

    suspend fun fetchWeatherByUserLocation(): Result<WeatherResponse> {
        return getUserLocation()?.let {
            performRequest { weatherService.getByLocation(it.latitude, it.longitude) }
        } ?: run {
            Result.Error(R.string.could_not_find_user)
        }
    }

    suspend fun fetchWeatherByCityId(cityId: Int): Result<WeatherResponse> {
        return performRequest { weatherService.getByCityId(cityId) }
    }
}