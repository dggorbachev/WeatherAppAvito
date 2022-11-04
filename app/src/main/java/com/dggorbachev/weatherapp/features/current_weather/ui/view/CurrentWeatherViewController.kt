package com.dggorbachev.weatherapp.features.current_weather.ui.view

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.core.view.isGone
import androidx.lifecycle.LifecycleOwner
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.common.Constants
import com.dggorbachev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import com.dggorbachev.weatherapp.features.current_weather.stateholders.CurrentWeatherViewModel
import java.util.*

class CurrentWeatherViewController(
    private val context: Context,
    private val binding: FragmentCurrentWeatherBinding,
    private val viewModel: CurrentWeatherViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
) {

    private val geocoder = Geocoder(context, Locale("ru"))

    fun setUpCurrentWeatherView() {
        bindLocation()
//        observeRemoteWeather(latitude = Constants.BASE_LATITUDE,
//            longitude = Constants.BASE_LONGITUDE)
        observeLocationWeather()
    }

    private fun bindLocation() {
        viewModel.getSavedLocation()
        viewModel.savedLocation.observe(viewLifecycleOwner) { region ->

            try {

                val addresses: List<Address> = geocoder.getFromLocationName(region, 1)
                observeRemoteWeather(
                    latitude = addresses[0].latitude,
                    longitude = addresses[0].longitude,
                )
            } catch (e: Exception) {

                viewModel.isRegionPicked.observe(viewLifecycleOwner) { isRegionPicked ->
                    if (!isRegionPicked) {
                        observeRemoteWeather(latitude = Constants.BASE_LATITUDE,
                            longitude = Constants.BASE_LONGITUDE)
                    } else {
                        bindNotification(true)
                        binding.tvNotification.text = "Не удалось найти данные"
                    }
                }
            }
        }
    }

    private fun observeLocationWeather() {
        viewModel.isLocationAllowed.observe(viewLifecycleOwner) {

            viewModel.getCoordinates()

            viewModel.coordinates.observe(viewLifecycleOwner) { coordinates ->
                observeRemoteWeather(latitude = coordinates.first, longitude = coordinates.second)
            }
        }
    }

    private fun observeRemoteWeather(latitude: Double, longitude: Double) {
        viewModel.getCurrentWeather(latitude, longitude)

        viewModel.currentWeather
            .observe(viewLifecycleOwner) { state ->
                when (state) {
                    is AsyncState.Loading -> {
                        binding.progressBar.isGone = false
                    }
                    is AsyncState.Loaded -> {
                        bindNotification(false)
                        bindWeatherInfo(state.data.latitude, state.data.longitude, state.data)
                    }
                    is AsyncState.Error -> {
                        bindNotification(true)
                        binding.tvNotification.text = state.message
                    }
                }
            }
    }

    private fun bindWeatherInfo(latitude: Double, longitude: Double, weather: CurrentWeather) {

        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        val cityName: String = addresses[0].locality
        val countryName: String = addresses[0].countryName

        binding.tvRegion.text = context.getString(R.string.region_message,
            cityName,
            countryName
        )
        binding.tvMinMaxTemp.text =
            context.getString(R.string.min_max_temp_message,
                weather.tempMax.toString(),
                weather.tempMin.toString()
            )

        binding.tvFeelsLike.text =
            context.getString(R.string.feels_like_message, weather.feelsLike.toString())

        binding.tvTemp.text =
            context.getString(R.string.temperature_message, weather.temp.toString())

        binding.tvMain.text = weather.description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }

        binding.tvHumidity.text =
            context.getString(R.string.humidity_message, weather.humidity.toString())

        binding.tvWind.text =
            context.getString(R.string.wind_speed_message, weather.windSpeed.toString())

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = weather.sunset * 1000

        binding.tvSunset.text =
            context.getString(R.string.sunset_message,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE))
    }


    private fun bindNotification(isError: Boolean) {
        val isGone = !isError

        binding.progressBar.isGone = true
        binding.ivNotification.isGone = isGone
        binding.tvNotification.isGone = isGone
    }
}