package com.dggorbachev.weatherapp.features.current_weather.ui.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.BaseFragment
import com.dggorbachev.weatherapp.base.LambdaFactory
import com.dggorbachev.weatherapp.base.observeOnce
import com.dggorbachev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.dggorbachev.weatherapp.domain.model.CurrentWeather
import com.dggorbachev.weatherapp.features.current_weather.stateholders.CurrentWeatherViewModel
import java.util.*
import javax.inject.Inject

class CurrentWeatherFragment :
    BaseFragment<FragmentCurrentWeatherBinding>(FragmentCurrentWeatherBinding::inflate) {

    @Inject
    lateinit var factory: CurrentWeatherViewModel.Factory

    private val viewModel: CurrentWeatherViewModel by viewModels {
        LambdaFactory(this) { stateHandle ->
            factory.create(
                stateHandle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentWeather(55.751244, 37.618423)
            .observeOnce(viewLifecycleOwner) { currentWeather ->

                binding.progressBar.visibility = View.GONE
                bindWeatherInfo(55.751244, 37.618423, currentWeather)
            }
    }

    private fun bindWeatherInfo(latitude: Double, longitude: Double, weather: CurrentWeather) {

        val geocoder = Geocoder(requireContext(), Locale("ru"))
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        val cityName: String = addresses[0].locality
        val countryName: String = addresses[0].countryName

        binding.tvRegion.text = requireContext().getString(R.string.region_message,
            cityName,
            countryName
        )
        binding.tvMinMaxTemp.text =
            requireContext().getString(R.string.min_max_temp_message,
                weather.tempMax.toString(),
                weather.tempMin.toString()
            )

        binding.tvFeelsLike.text =
            requireContext().getString(R.string.feels_like_message, weather.feelsLike.toString())

        binding.tvTemp.text =
            requireContext().getString(R.string.temperature_message, weather.temp.toString())

        binding.tvMain.text = weather.description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }

        binding.tvHumidity.text =
            requireContext().getString(R.string.humidity_message, weather.humidity.toString())

        binding.tvWind.text =
            requireContext().getString(R.string.wind_speed_message, weather.windSpeed.toString())

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = weather.sunset * 1000

        binding.tvSunset.text =
            requireContext().getString(R.string.sunset_message,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE))
    }
}