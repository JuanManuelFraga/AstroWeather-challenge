package com.astroweather.chooser.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.astroweather.chooser.domain.repository.ChooserRepository
import com.astroweather.chooser.ui.ChooserViewModel
import com.astroweather.util.Result
import com.astroweather.util.TestDispatcherProvider
import com.astroweather.util.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ChooserViewModelTest {

    private lateinit var repository: ChooserRepository
    private lateinit var viewModel: ChooserViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = ChooserViewModel(TestDispatcherProvider(), repository)
    }

    @Test
    fun givenSuccessResultThenEmitOptions() = runBlockingTest {
        whenever(repository.fetchOptions()).thenReturn(Result.Success(listOf()))

        viewModel.fetchOptions()

        assertNotNull(viewModel.chooserModel.getOrAwaitValue().showOptions)
    }

    @Test
    fun givenErrorResultThenEmitErrorMessage() = runBlockingTest {
        whenever(repository.fetchOptions()).thenReturn(Result.Error(1))

        viewModel.fetchOptions()

        assertNotNull(viewModel.chooserModel.getOrAwaitValue().showErrorMessage)
    }
}