package com.astroweather.common.retrofit

import com.astroweather.R
import com.astroweather.util.Result
import retrofit2.Response
import javax.inject.Inject

class RetrofitMapper @Inject constructor() {

    /**
     * Map response to result body
     *
     * @param response to convert
     * @return the body as a Result
     */
    fun <T: Any> map(response: Response<T>): Result<T> {
        if (response.isRequestFailed() || response.body() == null) {
            return Result.Error(R.string.unknown_error)
        }
        return Result.Success(response.body()!!)
    }
}
