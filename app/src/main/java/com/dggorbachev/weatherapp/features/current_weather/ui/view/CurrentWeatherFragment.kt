package com.dggorbachev.weatherapp.features.current_weather.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dggorbachev.weatherapp.base.BaseFragment
import com.dggorbachev.weatherapp.base.LambdaFactory
import com.dggorbachev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.dggorbachev.weatherapp.features.current_weather.stateholders.CurrentWeatherViewModel
import javax.inject.Inject

class CurrentWeatherFragment :
    BaseFragment<FragmentCurrentWeatherBinding>(FragmentCurrentWeatherBinding::inflate) {

    private var currentWeatherViewController: CurrentWeatherViewController? = null

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

        currentWeatherViewController =
            CurrentWeatherViewController(requireContext(),
                binding,
                viewModel,
                viewLifecycleOwner, requireActivity()).apply { setUpCurrentWeatherView() }
    }


    override fun onDestroyView() {
        currentWeatherViewController = null
        super.onDestroyView()
    }
}