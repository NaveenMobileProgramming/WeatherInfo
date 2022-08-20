package com.devcoder.weather.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


const val REQUEST_CODE_LOCATION = 100
fun checkLocationPermission(activity: Activity): Boolean {
    return try {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
            false
        } else true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}