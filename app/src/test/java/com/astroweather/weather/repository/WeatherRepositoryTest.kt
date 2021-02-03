package com.astroweather.weather.repository

import android.location.Location
import com.astroweather.common.retrofit.PerformRequestUseCase
import com.astroweather.util.Result
import com.astroweather.weather.domain.repository.WeatherRepository
import com.astroweather.weather.domain.service.WeatherService
import com.astroweather.weather.usecase.GetUserLocationUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private lateinit var getUserLocation: GetUserLocationUseCase
    private lateinit var performRequest: PerformRequestUseCase
    private lateinit var weatherService: WeatherService
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        getUserLocation = mock()
        performRequest = mock()
        weatherService = mock()
        repository = WeatherRepository(getUserLocation, performRequest, weatherService)
    }

    @Test
    fun givenFetchWeatherByUserLocationWithNullLocationThenRetrieveError() = runBlockingTest {
        whenever(getUserLocation()).thenReturn(null)

        val result = repository.fetchWeatherByUserLocation()

        assertTrue(result is Result.Error)
    }

    @Test
    fun givenFetchWeatherByUserLocationSuccessThenGetWeatherByLocation() = runBlockingTest {
        val userLocation: Location = mock()
        val mockLatitude = 34.65432
        val mockLongitude = -52.33471
        whenever(userLocation.latitude).thenReturn(mockLatitude)
        whenever(userLocation.longitude).thenReturn(mockLongitude)
        whenever(getUserLocation()).thenReturn(userLocation)

        repository.fetchWeatherByUserLocation()

        verify(performRequest) {
            weatherService.getByLocation(mockLatitude, mockLongitude)
        }
    }

    @Test
    fun givenFetchWeatherByCityIdSuccessThenGetWeatherByCityId() = runBlockingTest {
        val cityId = 65434

        repository.fetchWeatherByCityId(cityId)

        verify(performRequest) { weatherService.getByCityId(cityId) }
    }
}