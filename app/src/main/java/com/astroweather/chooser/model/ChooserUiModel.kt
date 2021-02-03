package com.astroweather.chooser.model

import androidx.annotation.StringRes

data class ChooserUiModel(
    val showOptions: List<ChooserModel>? = null,
    @StringRes val showErrorMessage: Int? = null
)