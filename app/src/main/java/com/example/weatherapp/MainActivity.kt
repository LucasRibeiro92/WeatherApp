package com.example.weatherapp

/*import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import android.Manifest
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.dc.WeatherResponse
import com.example.weatherapp.vm.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var temperatureTextView = binding.temperatureTextView

        // Inicialize o fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Inicializar o ViewModel
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.setMainActivity(this)

        // Verificar se a permissão de localização foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permissão ainda não concedida, solicitar permissão em tempo de execução
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permissão já concedida, iniciar a solicitação de localização
            requestLocationUpdates { latitude, longitude ->
                // Dentro do callback, você pode fazer a chamada da API do OpenWeatherMap com as coordenadas
                // Por exemplo, usando Retrofit ou Volley
                val weatherNow: Unit = viewModel.fetchWeather(latitude, longitude)
                temperatureTextView.setText("${weatherNow.temp}")
            }

        }
    }

    private fun requestLocationUpdates(callback: (Double, Double) -> Unit) {
        // Verificar permissões novamente
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obter a última localização conhecida
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Verificar se a localização é válida
                    if (location != null) {
                        // Extrair latitude e longitude
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Chamada do callback com as coordenadas
                        viewModel.fetchWeather(latitude, longitude) { weatherResponse, error ->
                            if (weatherResponse != null) {

                            } else {
                                // Lide com o erro
                            }
                        }
                    } else {
                        // A localização é nula, você pode tratar isso de acordo com sua lógica de negócios
                    }
                }
                .addOnFailureListener { e ->
                    // Lidar com falha ao obter a localização
                }
        }
    }

    // Método para lidar com o resultado da solicitação de permissão
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão de localização concedida, iniciar a solicitação de localização
                requestLocationUpdates { latitude, longitude ->
                    // Dentro do callback, você pode fazer a chamada da API do OpenWeatherMap com as coordenadas
                    // Por exemplo, usando Retrofit ou Volley
                    viewModel.fetchWeather(latitude, longitude)
                }
            } else {
                // Permissão de localização negada pelo usuário
                // Você pode lidar com isso exibindo uma mensagem de erro ou tomando outra ação apropriada
            }
        }
    }

    fun updateWeatherUI(weatherResponse: WeatherResponse?) {
        // Atualize a UI com os dados climáticos
        temperatureTextView.text = weatherResponse?.current?.temp.toString()
    }

    fun showError(errorMessage: String) {
        // Exiba uma mensagem de erro na UI
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}*/

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import android.Manifest
import android.location.Location
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.dc.WeatherResponse
import com.example.weatherapp.vm.WeatherViewModel
import com.example.weatherapp.vm.WeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val temperatureTextView = binding.temperatureTextView

        // Inicialize o fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Inicializar o ViewModel
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(this))[WeatherViewModel::class.java]


        // Verificar se a permissão de localização foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permissão ainda não concedida, solicitar permissão em tempo de execução
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permissão já concedida, iniciar a solicitação de localização
            val weatherNow = requestLocationUpdates()

        }
    }

    private fun requestLocationUpdates() {
        // Verificar permissões novamente
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obter a última localização conhecida
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Verificar se a localização é válida
                    if (location != null) {
                        // Extrair latitude e longitude
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Chamada do método para buscar o clima
                        viewModel.fetchWeather(latitude, longitude)
                    } else {
                        // A localização é nula, você pode tratar isso de acordo com sua lógica de negócios
                    }
                }
                .addOnFailureListener { e ->
                    // Lidar com falha ao obter a localização
                }
        }
    }

    // Método para lidar com o resultado da solicitação de permissão
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão de localização concedida, iniciar a solicitação de localização
                requestLocationUpdates()
            } else {
                // Permissão de localização negada pelo usuário
                // Você pode lidar com isso exibindo uma mensagem de erro ou tomando outra ação apropriada
            }
        }
    }

    // Método para atualizar a UI com os dados climáticos
    fun updateWeatherUI(weatherResponse: WeatherResponse?) {
        // Aqui você atualiza sua UI com os dados climáticos recebidos
        temperatureTextView.setText("${weatherNow}").
    }

    // Método para exibir mensagens de erro na UI
    fun showError(errorMessage: String) {
        // Exibe uma mensagem de erro na UI
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
