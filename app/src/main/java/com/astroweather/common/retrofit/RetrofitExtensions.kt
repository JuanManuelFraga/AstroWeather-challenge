package com.astroweather.common.retrofit

import retrofit2.Response

/**
 * Returns whether the request response represents a failure,
 * either due to connection error or due to error status code
 *
 * @return True if the request failed, false otherwise
 */
fun Response<*>.isRequestFailed(): Boolean {
    return this.isSuccessful.not() || this.code() in 400..599
}