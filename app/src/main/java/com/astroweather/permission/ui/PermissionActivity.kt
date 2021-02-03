package com.astroweather.permission.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.astroweather.R
import com.astroweather.permission.listener.GpsListener
import com.astroweather.util.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.permission_activity.*

@AndroidEntryPoint
class PermissionActivity : AppCompatActivity(), GpsListener {

    private val viewModel: PermissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_activity)
        setStatusBarColor(R.color.white, true)
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.havePermissionsAndGpsIsEnabled()) {
            onPermissionGranted()
        }
        if (viewModel.havePermissions()) {
            permissionButton.text = resources.getString(R.string.permission_location_button)
            permissionButton.setOnClickListener { TurnOnGpsAlert.display(this, this) }
        }
    }

    private fun checkPermission() {
        if (viewModel.havePermissionsAndGpsIsEnabled() ) {
            onPermissionGranted()
        } else {
            askForPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && viewModel.havePermissions()) {
            TurnOnGpsAlert.display(this, this)
            permissionButton.setOnClickListener { TurnOnGpsAlert.display(this, this) }
        } else if (hasNotAnyPermissionPermanentlyDenied()) {
            permissionButton.setOnClickListener { askForPermission() }
        } else {
            updateLandingStateToPermanentlyDenied()
        }
        if (viewModel.havePermissionsAndGpsIsEnabled()) {
            onPermissionGranted()
        }
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), REQUEST_CODE_PERMISSION)
    }

    override fun onGpsTurnOn() {
        onPermissionGranted()
    }

    private fun updateLandingStateToPermanentlyDenied() {
        permissionButton.text = resources.getString(R.string.permission_rejected_settings)
        permissionButton.setOnClickListener { openApplicationSettings() }
    }

    private fun openApplicationSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts(PACKAGE_DIR, packageName, null)
        startActivity(intent)
    }

    private fun hasNotAnyPermissionPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)
        } else {
            false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onPermissionRejected()
    }

    private fun onPermissionGranted() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun onPermissionRejected() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 12
        private const val PACKAGE_DIR = "package"

        fun newIntent(context: Context) = Intent(context, PermissionActivity::class.java)
    }
}