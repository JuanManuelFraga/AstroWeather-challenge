package com.astroweather.util

import androidx.annotation.StringRes

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(@StringRes val errorMessageRes: Int) : Result<Nothing>()
}