package com.astroweather.weather.ui

import androidx.annotation.StringRes
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroweather.R
import com.astroweather.chooser.domain.source.local.CityRegistry
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.weather.domain.response.WeatherResponse
import com.astroweather.weather.domain.repository.WeatherRepository
import com.astroweather.util.coroutines.DispatcherProvider
import com.astroweather.util.Result
import com.astroweather.weather.mapper.WeatherMapper
import com.astroweather.weather.model.WeatherModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val mapper: WeatherMapper,
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherModel = MutableLiveData<WeatherUiModel>()
    val weatherModel: LiveData<WeatherUiModel>
        get() = _weatherModel

    private var isAlreadyConsumed: Boolean = false
    private lateinit var currentFetchAction: suspend () -> Result<WeatherResponse>

    fun fetchWeatherIfShould() = viewModelScope.launch(dispatcherProvider.io()) {
        if (isAlreadyConsumed.not()) {
            isAlreadyConsumed = true
            updateUi(showSkeleton = true)
            fetchWeatherIn(CityRegistry.MONTEVIDEO.model.cityId!!) /* By default show Montevideo weather */
        }
    }

    fun fetchCurrentWeather() = viewModelScope.launch(dispatcherProvider.io()) {
        updateUi(showLoading = true)
        currentFetchAction = { repository.fetchWeatherByUserLocation() }
        fetchWeather()
        updateUi(showLocationInfo = ChooserModel(R.string.chooser_user_option, R.drawable.location_icon))
    }

    fun fetchWeatherIn(cityId: Int) = viewModelScope.launch(dispatcherProvider.io()) {
        updateUi(showLoading = true)
        currentFetchAction = { repository.fetchWeatherByCityId(cityId) }
        fetchWeather()
        updateUi(showLocationInfo = CityRegistry.getCityById(cityId).model)
    }

    fun updateWeather() = viewModelScope.launch(dispatcherProvider.io()) {
        updateUi(showLoading = true)
        fetchWeather()
    }

    private suspend fun fetchWeather() {
        when (val result = currentFetchAction()) {
            is Result.Success -> onResultSuccess(result)
            is Result.Error -> onResultError(result)
        }
    }

    private suspend fun onResultError(result: Result.Error) {
        updateUi(showErrorMessage = result.errorMessageRes)
    }

    private suspend fun onResultSuccess(result: Result.Success<WeatherResponse>) {
        updateUi(showLoading = false, showWeatherInfo = mapper.map(result.data))
    }

    private suspend fun updateUi(
        showLoading: Boolean = false,
        showSkeleton: Boolean = false,
        showLocationInfo: ChooserModel? = null,
        showWeatherInfo: WeatherModel? = null,
        @StringRes showErrorMessage: Int? = null
    ) = withContext(dispatcherProvider.ui()) {
        _weatherModel.value = WeatherUiModel(showLoading, showSkeleton, showWeatherInfo, showLocationInfo, showErrorMessage)
    }
}