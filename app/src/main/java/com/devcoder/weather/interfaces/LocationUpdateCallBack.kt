package com.devcoder.weather.interfaces

import android.location.Location


interface LocationUpdateCallBack {

    fun onLocationUpdate(location: Location)

}