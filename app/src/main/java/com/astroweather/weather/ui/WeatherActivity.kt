package com.astroweather.weather.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.astroweather.R
import com.astroweather.chooser.listener.ChooserListener
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.chooser.ui.ChooserBottomSheet
import com.astroweather.util.setStatusBarColor
import com.astroweather.permission.ui.PermissionActivity
import com.astroweather.weather.model.WeatherModel
import com.astroweather.weather.model.WeatherPropertyModel
import com.astroweather.weather.ui.view.WeatherRowSkeletonView
import com.astroweather.weather.ui.view.WeatherRowView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_activity.*
import kotlinx.android.synthetic.main.weather_error_layout.*
import kotlinx.android.synthetic.main.weather_layout.*

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity(), ChooserListener {

    private var currentWeather: WeatherModel? = null
    private var currentLocation: ChooserModel? = null

    private val viewModel: WeatherViewModel by viewModels()
    private val listenUpdates = Observer { model: WeatherUiModel ->
        showLoadingIfShould(model.showLoading)
        showSkeletonIfShould(model.showSkeleton)
        model.showWeather?.let { showWeather(it) }
        model.showLocationInfo?.let { showLocationInfo(it) }
        model.showErrorMessage?.let { showError(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        viewModel.weatherModel.observe(this, listenUpdates)
        initView()
        viewModel.fetchWeatherIfShould()
        restoreInstanceIfShould(savedInstanceState)
    }

    private fun restoreInstanceIfShould(savedInstanceState: Bundle?) {
        savedInstanceState?.let { bundle ->
            val weather: WeatherModel? = bundle.getParcelable(WEATHER_KEY)
            weather?.let { showWeather(it) }
            val chooser: ChooserModel? = bundle.getParcelable(CHOOSER_KEY)
            chooser?.let { showLocationInfo(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(WEATHER_KEY, currentWeather)
        outState.putParcelable(CHOOSER_KEY, currentLocation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_SUCCESS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onPermissionGranted()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                currentLocation?.let { showLocationInfo(it) }
                currentWeather?.let { showWeather(it) }
            }
        }
    }

    override fun onChoosingOption(cityId: Int?) {
        cityId?.let {
            viewModel.fetchWeatherIn(it)
        } ?: run {
            showPermissionLanding()
        }
    }

    private fun initView() {
        setStatusBarColor(R.color.blue, false)
        swipeToRefresh.setOnRefreshListener { viewModel.updateWeather() }
        changeCityButton.setOnClickListener { openChooserBottomSheet() }
        errorButton.setOnClickListener { viewModel.updateWeather() }
    }

    private fun onPermissionGranted() {
        viewModel.fetchCurrentWeather()
    }

    private fun showWeather(weatherModel: WeatherModel) {
        viewFlipper.displayedChild = CONTENT_INDEX
        currentWeather = weatherModel
        weatherParent.scheduleLayoutAnimation()
        weatherModel.weatherStyle?.let {
            weatherParent.background = ContextCompat.getDrawable(this, it.background)
            weatherLottie.setAnimation(it.lottie)
            weatherLottie.playAnimation()
        } ?: run {
            weatherParent.background = ContextCompat.getDrawable(this, R.drawable.day_background)
            weatherLottie.setAnimation(R.raw.clear_sky)
            setStatusBarColor(R.color.blue, false)
        }
        weatherTemperature.text = weatherModel.temperature
        weatherTitle.text = weatherModel.title
        weatherModel.subtitle?.let { weatherSubtitle.text = it }
        weatherTemperatureDegrees.visibility = VISIBLE
        showProperties(weatherModel.properties)
        weatherModel.primaryColor?.let {
            setStatusBarColor(it, false)
            changeCityButton.backgroundTintList = ContextCompat.getColorStateList(this, it)
            changeCityButton.visibility = VISIBLE
        }
    }

    private fun showLocationInfo(currentLocation: ChooserModel) {
        this.currentLocation = currentLocation
        locationTitle.text = getString(currentLocation.nameRes)
        locationIcon.setImageResource(currentLocation.icon)
    }

    private fun showProperties(properties: List<WeatherPropertyModel>) {
        weatherPropertiesContainer.removeAllViews()
        properties.forEach {
            weatherPropertiesContainer.addView(WeatherRowView(this).apply { bind(it) })
        }
    }

    private fun showLoadingIfShould(shouldShowLoading: Boolean) {
        swipeToRefresh.isRefreshing = shouldShowLoading
    }

    private fun showSkeletonIfShould(showSkeleton: Boolean) {
        if (showSkeleton) {
            for (i in 0..2) {
                weatherPropertiesContainer.addView(WeatherRowSkeletonView(this))
            }
        }
    }

    private fun openChooserBottomSheet() {
        ChooserBottomSheet.newInstance().show(supportFragmentManager, ChooserBottomSheet.TAG)
    }

    private fun showPermissionLanding() {
        val permissionActivity = PermissionActivity.newIntent(this)
        permissionActivity.action = Intent.ACTION_VIEW
        startActivityForResult(permissionActivity, PERMISSION_SUCCESS_REQUEST_CODE)
    }

    private fun showError(errorMessage: Int) {
        errorDescription.text = getString(errorMessage)
        viewFlipper.displayedChild = ERROR_INDEX
    }

    companion object {
        private const val WEATHER_KEY = "WEATHER_KEY"
        private const val CHOOSER_KEY = "CHOOSER_KEY"
        private const val PERMISSION_SUCCESS_REQUEST_CODE = 7672
        private const val CONTENT_INDEX = 0
        private const val ERROR_INDEX = 1
    }
}