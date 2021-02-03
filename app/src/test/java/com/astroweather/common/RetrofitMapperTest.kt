package com.astroweather.common

import com.astroweather.common.retrofit.RetrofitMapper
import com.astroweather.util.Result
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RetrofitMapperTest {

    private lateinit var retrofitMapper: RetrofitMapper

    @Before
    fun setUp() {
        retrofitMapper = RetrofitMapper()
    }

    @Test
    fun givenRequestFailThenRetrieveUnKnowError() {
        val requestFailResponse = Response.error<String>(500, mock())

        val result = retrofitMapper.map(requestFailResponse)

        assertTrue(result is Result.Error)
    }

    @Test
    fun givenNullBodyThenRetrieveUnKnowError() {
        val nullBodyResponse = Response.success(null)

        val result = retrofitMapper.map(nullBodyResponse)

        assertTrue(result is Result.Error)
    }

    @Test
    fun givenSuccessResponseWithNotNullBodyThenRetrieveItAsAResult() {
        val successBody = "weather"
        val successResponse = Response.success(successBody)

        val result = retrofitMapper.map(successResponse)

        assertEquals((result as Result.Success).data, successBody)
    }
}