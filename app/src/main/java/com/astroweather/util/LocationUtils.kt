package com.astroweather.util

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationUtils @Inject constructor(@ApplicationContext private val applicationContext: Context) {

    /**
     * Check application permissions
     *
     * @return true if have permission
     */
    fun havePermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    }

    /**
     * Check gps state
     *
     * @return true if gps is enabled
     */
    fun isEnabled(): Boolean {
        val manager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(GPS_PROVIDER) || manager.isProviderEnabled(NETWORK_PROVIDER)
    }
}