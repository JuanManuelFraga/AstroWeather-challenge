package com.astroweather.chooser.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChooserModel(
    @StringRes val nameRes: Int,
    @DrawableRes val icon: Int,
    val cityId: Int? = null
): Parcelable