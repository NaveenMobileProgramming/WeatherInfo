package com.devcoder.weather.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Clouds (

  @SerializedName("all" ) var all : Int? = null

): Parcelable