package com.devcoder.weather.utils

import kotlin.math.roundToInt

fun convertKelvinToCelsius(temp: Double?): Int {
    return if (temp != null)
        (temp - 273.15).roundToInt().dec()
    else 0
}