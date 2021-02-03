package com.astroweather.weather.usecase

import javax.inject.Inject

class IsDayIconUseCase @Inject constructor() {

    operator fun invoke(icon: String): Boolean {
        return icon.any { it == 'd' }
    }
}