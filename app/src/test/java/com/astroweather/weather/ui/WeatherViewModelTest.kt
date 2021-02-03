package com.astroweather.weather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.astroweather.R
import com.astroweather.chooser.domain.source.local.CityRegistry
import com.astroweather.util.TestDispatcherProvider
import com.astroweather.util.getOrAwaitValue
import com.astroweather.weather.domain.repository.WeatherRepository
import com.astroweather.weather.mapper.WeatherMapper
import com.astroweather.weather.ui.WeatherViewModel
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var repository: WeatherRepository
    private lateinit var mapper: WeatherMapper
    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        mapper = mock()
        viewModel = WeatherViewModel(TestDispatcherProvider(), mapper, repository)
    }

    @Test
    fun givenFetchWeatherAtFirstTimeThenRetrieveMontevideoCityWeather() = runBlockingTest {
        whenever(repository.fetchWeatherByCityId(any())).thenReturn(mock())

        viewModel.fetchWeatherIfShould()

        verify(repository).fetchWeatherByCityId(CityRegistry.MONTEVIDEO.model.cityId!!)
    }

    @Test
    fun givenFetchWeatherIfShouldTwiceThenFetchResultOnlyOnes() = runBlockingTest {
        whenever(repository.fetchWeatherByCityId(any())).thenReturn(mock())

        viewModel.fetchWeatherIfShould()
        viewModel.fetchWeatherIfShould()

        verify(repository, times(1)).fetchWeatherByCityId(any())
    }

    @Test
    fun givenFetchCurrentWeatherThenFetchUserLocationWeather() = runBlockingTest {
        whenever(repository.fetchWeatherByUserLocation()).thenReturn(mock())

        viewModel.fetchCurrentWeather()

        verify(repository).fetchWeatherByUserLocation()
    }

    @Test
    fun givenFetchCurrentWeatherThenEmitUserLocationInfo() = runBlockingTest {
        whenever(repository.fetchWeatherByUserLocation()).thenReturn(mock())

        viewModel.fetchCurrentWeather()

        assertEquals(viewModel.weatherModel.getOrAwaitValue().showLocationInfo?.nameRes, R.string.chooser_user_option)
    }

    @Test
    fun givenFetchWeatherInCityThenCallRepositoryAndRetrieveResult() = runBlockingTest {
        val city = CityRegistry.BUENOS_AIRES.model
        whenever(repository.fetchWeatherByCityId(city.cityId!!)).thenReturn(mock())

        viewModel.fetchWeatherIn(city.cityId!!)

        verify(repository).fetchWeatherByCityId(city.cityId!!)
    }

    @Test
    fun givenFetchWeatherInCityThenShowLocationInfo() = runBlockingTest {
        val city = CityRegistry.BUENOS_AIRES.model
        whenever(repository.fetchWeatherByCityId(any())).thenReturn(mock())

        viewModel.fetchWeatherIn(city.cityId!!)

        assertEquals(viewModel.weatherModel.getOrAwaitValue().showLocationInfo?.nameRes, city.nameRes)
    }

    @Test
    fun givenUpdateWeatherThenRefreshLastAction() = runBlockingTest {
        whenever(repository.fetchWeatherByUserLocation()).thenReturn(mock())
        viewModel.fetchCurrentWeather() /* last action */

        viewModel.updateWeather()

        verify(repository, times(2)).fetchWeatherByUserLocation()
    }
}