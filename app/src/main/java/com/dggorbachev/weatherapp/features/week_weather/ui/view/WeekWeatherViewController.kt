package com.dggorbachev.weatherapp.features.week_weather.ui.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.common.Constants
import com.dggorbachev.weatherapp.databinding.FragmentWeekWeatherBinding
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.domain.model.WeekWeather
import com.dggorbachev.weatherapp.features.week_weather.stateholders.WeekWeatherViewModel
import com.dggorbachev.weatherapp.features.week_weather.ui.view.adapter.WeekWeatherAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class WeekWeatherViewController(
    private val context: Context,
    private val binding: FragmentWeekWeatherBinding,
    private val viewModel: WeekWeatherViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val activity: FragmentActivity,
) {

    private val adapter: WeekWeatherAdapter by lazy { WeekWeatherAdapter() }
    private val geocoder = Geocoder(context, Locale("ru", "RU"))

    fun setUpWeekWeatherView() {
        bindMenu()
        bindRecyclerView()
        bindLocation()
        observeLocationWeather()
    }

    private fun bindRecyclerView() {
        val recyclerView = binding.rvWeek
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
        viewModel.getWeekWeather(latitude, longitude)

        viewModel.weather
            .observe(viewLifecycleOwner) { state ->
                when (state) {
                    is AsyncState.Loading -> {
                        binding.progressBar.isGone = false
                    }
                    is AsyncState.Loaded -> {
                        bindNotification(false)
                        adapter.submitList(state.data)
                        bindWeatherInfo(state.data)
                    }
                    is AsyncState.Error -> {
                        bindNotification(true)
                        binding.tvNotification.text = state.message
                    }
                }
            }
    }

    private fun bindWeatherInfo(weekWeather: List<WeekWeather>) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale("ru", "RU"))
            val addresses: List<Address> =
                geocoder.getFromLocation(weekWeather[0].latitude, weekWeather[0].longitude, 1)
            val cityName: String = addresses[0].locality
            val countryName: String = addresses[0].countryName
            withContext(Dispatchers.Main) {
                binding.tvRegion.text = context.getString(R.string.region_message,
                    cityName,
                    countryName
                )
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
                                .navigate(R.id.action_weekWeatherFragment_to_searchFragment)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}