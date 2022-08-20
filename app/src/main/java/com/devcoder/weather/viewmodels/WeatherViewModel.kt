package com.devcoder.weather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcoder.weather.models.CurrentWeatherResponse
import com.devcoder.weather.network.ifError
import com.devcoder.weather.network.ifSuccess
import com.devcoder.weather.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var weatherRepo: WeatherRepo) : ViewModel() {

    var weatherInfoObserver: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    var progressBarInfoObserver: MutableLiveData<Boolean> = MutableLiveData()

    fun getCurrentWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch {
            progressBarInfoObserver.postValue(true)
            weatherRepo.getWeatherInfo(lat, lon).ifSuccess {
                weatherInfoObserver.postValue(it)
                progressBarInfoObserver.postValue(false)
            }.ifError {
                progressBarInfoObserver.postValue(false)
            }
        }
    }
}