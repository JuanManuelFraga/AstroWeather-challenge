package com.astroweather.weather.domain.service

import com.astroweather.BuildConfig
import com.astroweather.weather.domain.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather?appid=" + BuildConfig.API_KEY)
    suspend fun getByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Response<WeatherResponse>

    @GET("/data/2.5/weather?appid=" + BuildConfig.API_KEY)
    suspend fun getByCityId(@Query("id") cityId: Int): Response<WeatherResponse>
}