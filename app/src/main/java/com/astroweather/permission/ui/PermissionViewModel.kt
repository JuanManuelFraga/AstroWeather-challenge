package com.astroweather.permission.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.astroweather.util.LocationUtils

class PermissionViewModel @ViewModelInject constructor(
    private var locationUtils: LocationUtils
) : ViewModel() {

    fun havePermissionsAndGpsIsEnabled(): Boolean {
        return locationUtils.havePermission() && locationUtils.isEnabled()
    }

    fun havePermissions(): Boolean {
        return locationUtils.havePermission()
    }
}