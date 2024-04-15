package com.example.weatherapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.MainActivity
import com.example.weatherapp.vm.WeatherViewModel

class WeatherViewModelFactory(private val mainActivity: MainActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(mainActivity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
