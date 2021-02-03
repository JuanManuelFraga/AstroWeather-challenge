package com.astroweather.common

import com.astroweather.R
import com.astroweather.common.retrofit.PerformRequestUseCase
import com.astroweather.common.retrofit.RetrofitMapper
import com.astroweather.util.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class PerformRequestUseCaseTest {

    private lateinit var retrofitMapper: RetrofitMapper
    private lateinit var performRequest: PerformRequestUseCase

    @Before
    fun setUp() {
        retrofitMapper = mock()
        performRequest = PerformRequestUseCase(retrofitMapper)
    }

    @Test
    fun givenFetchDataWithoutInternetThenRetrieveConnectionError() = runBlockingTest {
        val exceptionRequest: () -> Response<String> = { throw IOException() }

        val result = performRequest { exceptionRequest() }

        assertTrue((result as Result.Error).errorMessageRes == R.string.connection_error)
    }

    @Test
    fun givenFetchDataSuccessThenRetrieveMapResult() = runBlockingTest {
        val responseBody = "result"
        val response = Response.success(responseBody)
        val successRequest: () -> Response<String> = { response }
        whenever(retrofitMapper.map(response)).thenReturn(Result.Success(responseBody))

        val result = performRequest { successRequest() }

        assertEquals((result as Result.Success).data, responseBody)
    }
}