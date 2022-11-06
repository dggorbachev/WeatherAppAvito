package com.dggorbachev.weatherapp.features.week_weather.ui.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.BaseFragment
import com.dggorbachev.weatherapp.base.LambdaFactory
import com.dggorbachev.weatherapp.base.common.Constants.BASE_LATITUDE
import com.dggorbachev.weatherapp.base.common.Constants.BASE_LONGITUDE
import com.dggorbachev.weatherapp.databinding.FragmentWeekWeatherBinding
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.features.week_weather.stateholders.WeekWeatherViewModel
import com.dggorbachev.weatherapp.features.week_weather.ui.view.adapter.WeekWeatherAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class WeekWeatherFragment :
    BaseFragment<FragmentWeekWeatherBinding>(FragmentWeekWeatherBinding::inflate) {

    private var weekWeatherViewController: WeekWeatherViewController? = null

    @Inject
    lateinit var factory: WeekWeatherViewModel.Factory


    private val viewModel: WeekWeatherViewModel by viewModels {
        LambdaFactory(this) { stateHandle ->
            factory.create(
                stateHandle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weekWeatherViewController = WeekWeatherViewController(requireContext(),
            binding,
            viewModel,
            viewLifecycleOwner,
            requireActivity()).apply { setUpWeekWeatherView() }
    }

    override fun onDestroyView() {
        weekWeatherViewController = null
        super.onDestroyView()
    }
}