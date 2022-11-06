package com.dggorbachev.weatherapp.features.current_weather.ui.view

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.common.Constants
import com.dggorbachev.weatherapp.base.exts.StringExt.firstCharUpper
import com.dggorbachev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.Weather
import com.dggorbachev.weatherapp.features.current_weather.stateholders.CurrentWeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CurrentWeatherViewController(
    private val context: Context,
    private val binding: FragmentCurrentWeatherBinding,
    private val viewModel: CurrentWeatherViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val activity: FragmentActivity,
) {

    private val geocoder = Geocoder(context, Locale("ru"))

    fun setUpCurrentWeatherView() {
        bindMenu()
        bindLocation()
        observeLocationWeather()
    }

    private fun bindLocation() {
        viewModel.getSavedLocation()
        viewModel.savedLocation.observe(viewLifecycleOwner) { region ->

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val addresses: List<Address> = geocoder.getFromLocationName(region, 1)

                    withContext(Dispatchers.Main) {
                        observeRemoteWeather(
                            latitude = addresses[0].latitude,
                            longitude = addresses[0].longitude,
                        )
                    }
                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {
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

        viewModel.weather
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

    private fun bindWeatherInfo(latitude: Double, longitude: Double, weather: Weather) {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            val cityName: String = addresses[0].locality
            val countryName: String = addresses[0].countryName

            withContext(Dispatchers.Main) {
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

                binding.tvMain.text = weather.description.firstCharUpper()

                binding.tvHumidity.text =
                    context.getString(R.string.humidity_message, weather.humidity.toString())

                binding.tvWind.text =
                    context.getString(R.string.wind_speed_message, weather.windSpeed.toString())
            }

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = weather.sunset * 1000

            withContext(Dispatchers.Main) {
                binding.tvSunset.text =
                    context.getString(R.string.sunset_message,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE))
            }
        }
    }

    private fun bindNotification(isError: Boolean) {
        val isGone = !isError

        binding.progressBar.isGone = true
        binding.ivNotification.isGone = isGone
        binding.tvNotification.isGone = isGone
    }

    private fun bindMenu() {
        val menuHost: MenuHost = activity

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            binding.root.findNavController()
                                .navigate(R.id.action_currentWeatherFragment_to_searchFragment)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}