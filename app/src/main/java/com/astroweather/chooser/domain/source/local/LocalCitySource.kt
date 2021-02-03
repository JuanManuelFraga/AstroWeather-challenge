package com.astroweather.chooser.domain.source.local

import com.astroweather.chooser.domain.source.CitySource
import com.astroweather.chooser.model.ChooserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalCitySource @Inject constructor() : CitySource {

    override suspend fun getCities(): List<ChooserModel> {
        return CityRegistry.values().map { it.model }
    }
}