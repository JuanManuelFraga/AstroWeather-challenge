package com.astroweather.util

import com.astroweather.util.coroutines.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun ui() = sharedTestCoroutineDispatcher
    override fun io() = sharedTestCoroutineDispatcher
    override fun computation() = sharedTestCoroutineDispatcher
}