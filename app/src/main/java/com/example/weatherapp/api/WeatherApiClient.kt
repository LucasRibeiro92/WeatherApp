package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiClient {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/3.0/"
        const val API_KEY = "6aecdceab5208708eb7fdf190e00e5d1"

        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}