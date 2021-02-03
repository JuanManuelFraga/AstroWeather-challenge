package com.astroweather.weather.usecase

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.invocation.InvocationOnMock
import java.lang.IllegalStateException
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class GetUserLocationUseCaseTest {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cancellationTokenSource: CancellationTokenSource
    private lateinit var getUserLocation: GetUserLocationUseCase

    @Before
    fun setUp() {
        fusedLocationClient = mock()
        cancellationTokenSource = mock()
        getUserLocation = GetUserLocationUseCase(fusedLocationClient, cancellationTokenSource)
    }

    @Test
    fun givenFetchWeatherByUserLocationWithNullLocationThenRetrieveNull() = runBlockingTest {
        val currentLocationTask: Task<Location> = mockErrorTask()
        mockCurrentLocation(currentLocationTask)

        val userLocation = getUserLocation()

        assertNull(userLocation)
    }

    @Test
    fun givenFetchWeatherByUserLocationWithLocationThenRetrieveIt() = runBlockingTest {
        val mockLocation: Location = mock()
        val currentLocationTask: Task<Location> = mockSuccessTask(mockLocation)
        mockCurrentLocation(currentLocationTask)

        val userLocation = getUserLocation()

        assertEquals(userLocation, mockLocation)
    }

    private fun mockErrorTask(): Task<Location> {
        val currentLocationTask: Task<Location> = mock()
        doAnswer { invocation: InvocationOnMock ->
            val listener = invocation.arguments[0] as OnCompleteListener<Location>
            listener.onComplete(Tasks.forException(IllegalStateException()))
            null
        }.whenever(currentLocationTask).addOnCompleteListener(any())
        return currentLocationTask
    }

    private fun mockSuccessTask(mockLocation: Location): Task<Location> {
        val currentLocationTask: Task<Location> = mock()
        doAnswer { invocation: InvocationOnMock ->
            val listener = invocation.arguments[0] as OnCompleteListener<Location>
            listener.onComplete(Tasks.forResult(mockLocation))
            null
        }.whenever(currentLocationTask).addOnCompleteListener(any())
        return currentLocationTask
    }

    private fun mockCurrentLocation(mockTask: Task<Location>) {
        val mockToken: CancellationToken = mock()
        whenever(cancellationTokenSource.token).thenReturn(mockToken)
        whenever(fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, mockToken)).thenReturn(mockTask)
    }
}