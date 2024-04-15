package com.example.weatherapp.vm

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.weatherapp.MainActivity
import com.example.weatherapp.api.WeatherApiClient
import com.example.weatherapp.dc.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*class WeatherViewModel : ViewModel() {

    fun fetchWeather(latitude: Double, longitude: Double, callback: (WeatherResponse?, Throwable?) -> Unit) {
        val service = WeatherApiClient.create()
        service.getWeather(latitude, longitude, apiKey = WeatherApiClient.API_KEY)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        callback(weatherResponse, null)
                    } else {
                        callback(null, Exception("Failed to fetch weather data: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    callback(null, t)
                }
            })
    }
}*/

class WeatherViewModel(@SuppressLint("StaticFieldLeak") private val mainActivity: MainActivity) : ViewModel() {

    fun fetchWeather(latitude: Double, longitude: Double) {
        val service = WeatherApiClient.create()
        service.getWeather(latitude, longitude, apiKey = WeatherApiClient.API_KEY)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        mainActivity.updateWeatherUI(weatherResponse)
                    } else {
                        mainActivity.showError("Erro ao obter dados climáticos: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    mainActivity.showError("Falha na solicitação de dados climáticos: ${t.message}")
                }
            })
    }
}