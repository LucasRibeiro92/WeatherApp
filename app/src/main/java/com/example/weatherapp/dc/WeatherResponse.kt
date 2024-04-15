package com.example.weatherapp.dc

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val temp: Double,
    val description: String,
    val humidity: Int
)
