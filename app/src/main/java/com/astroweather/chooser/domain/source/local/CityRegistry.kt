package com.astroweather.chooser.domain.source.local

import com.astroweather.R
import com.astroweather.chooser.model.ChooserModel

enum class CityRegistry(val model: ChooserModel) {

    MONTEVIDEO(ChooserModel(R.string.montevideo, R.drawable.city_uruguay_flag_icon, 3441575)),
    LONDON(ChooserModel(R.string.london, R.drawable.city_uk_flag_icon, 2643743)),
    SAO_PAULO(ChooserModel(R.string.sao_paulo, R.drawable.city_brazil_flag_icon, 3448439)),
    BUENOS_AIRES(ChooserModel(R.string.buenos_aires, R.drawable.city_argentinan_flag_icon, 3435910)),
    MUNICH(ChooserModel(R.string.munich, R.drawable.city_germany_flag_icon, 2867714)),
    BEIJING(ChooserModel(R.string.beijing, R.drawable.city_beijing_flag_icon, 1816670)),
    ;

    companion object {

        fun getCityById(cityId: Int): CityRegistry {
            return values().first { it.model.cityId == cityId }
        }
    }
}