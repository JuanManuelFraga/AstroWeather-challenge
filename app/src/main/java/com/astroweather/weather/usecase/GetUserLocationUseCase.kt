package com.astroweather.weather.usecase

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private var cancellationTokenSource: CancellationTokenSource
) {

    private val locationCompletableDeferred = CompletableDeferred<Location?>()

    @SuppressLint("MissingPermission")
    suspend operator fun invoke(): Location? {
        val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
        currentLocationTask.addOnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful) {
                locationCompletableDeferred.complete(task.result)
            } else {
                locationCompletableDeferred.complete(null)
            }
        }
        return locationCompletableDeferred.await()
    }
}


