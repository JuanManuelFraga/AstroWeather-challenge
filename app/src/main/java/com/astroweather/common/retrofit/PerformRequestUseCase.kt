package com.astroweather.common.retrofit

import com.astroweather.R
import com.astroweather.util.Result
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PerformRequestUseCase @Inject constructor(private val mapper: RetrofitMapper) {

    /**
     * Utility class that catches IOException if happen and try to map the service response to a result
     */
    suspend operator fun <T: Any> invoke(requestFunc: suspend () -> Response<T>): Result<T> {
        return try {
            val result = requestFunc()
            mapper.map(result)
        } catch (e: IOException) {
            Result.Error(R.string.connection_error)
        }
    }
}