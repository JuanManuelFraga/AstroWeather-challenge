package com.astroweather.chooser.domain.source

import com.astroweather.chooser.model.ChooserModel

interface CitySource {

    /**
     * Retrieve list of city options
     */
    suspend fun getCities(): List<ChooserModel>?
}