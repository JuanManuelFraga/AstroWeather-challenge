package com.astroweather.chooser.repository

import com.astroweather.chooser.domain.repository.ChooserRepository
import com.astroweather.chooser.domain.source.CitySource
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.util.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChooserRepositoryTest {

    private lateinit var citySource: CitySource
    private lateinit var repository: ChooserRepository

    @Before
    fun setUp() {
        citySource = mock()
        repository = ChooserRepository(citySource)
    }

    @Test
    fun givenFetchNullCitiesThenRetrieveError() = runBlockingTest {
        whenever(citySource.getCities()).thenReturn(null)

        assertTrue(repository.fetchOptions() is Result.Error)
    }

    @Test
    fun givenFetchAnyCitYThenRetrieveItAsAResultPlusUserOption() = runBlockingTest {
        val emptyCities = listOf<ChooserModel>()
        whenever(citySource.getCities()).thenReturn(emptyCities)

        val result = repository.fetchOptions()

        assertEquals((result as Result.Success).data.size, emptyCities.size + 1)
    }
}