package com.example.weatherapp.api

import com.example.weatherapp.dc.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "hourly,daily,minutely",
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}