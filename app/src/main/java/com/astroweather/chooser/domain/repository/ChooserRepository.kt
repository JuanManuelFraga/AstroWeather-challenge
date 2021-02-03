package com.astroweather.chooser.domain.repository

import com.astroweather.R
import com.astroweather.chooser.domain.source.CitySource
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.util.Result
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ChooserRepository @Inject constructor(
    @Named("localCitySource") private val citySource: CitySource
) {

    suspend fun fetchOptions(): Result<List<ChooserModel>> {
        return citySource.getCities()?.let {
            val options = mutableListOf<ChooserModel>().apply {
                add(createUserLocationOption())
                addAll(it)
            }
            Result.Success(options)
        } ?: run {
            Result.Error(R.string.unknown_error)
        }
    }

    private fun createUserLocationOption(): ChooserModel {
        return ChooserModel(R.string.chooser_user_option, R.drawable.location_icon)
    }
}