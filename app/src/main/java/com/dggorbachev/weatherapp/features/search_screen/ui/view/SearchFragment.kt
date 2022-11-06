package com.dggorbachev.weatherapp.features.search_screen.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.BaseFragment
import com.dggorbachev.weatherapp.base.LambdaFactory
import com.dggorbachev.weatherapp.databinding.FragmentSearchBinding
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.features.search_screen.stateholders.SearchViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    @Inject
    lateinit var factory: SearchViewModel.Factory

    private val viewModel: SearchViewModel by viewModels {
        LambdaFactory(this) { stateHandle ->
            factory.create(
                stateHandle
            )
        }
    }

    private val baseCities =
        arrayListOf("Москва, Россия", "Санкт-Петербург, Россия", "Воронеж, Россия")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindAutoComplete()
        bindViewListeners()
        bindMenu()
    }

    private fun bindAutoComplete() {
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line, baseCities)

        with(binding) {
            actvCity.threshold = 1

            actvCity.setAdapter(adapter)

            actvCity.addTextChangedListener { text ->
                adapter.clear()

                viewModel.getHints(text.toString())
                viewModel.currentHints.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is AsyncState.Loading -> {
                            progressBar.isGone = false
                        }
                        is AsyncState.Loaded -> {
                            bindNotification(false)
                            adapter.addAll(state.data)
                            adapter.notifyDataSetChanged()
                        }
                        is AsyncState.Error -> {
                            binding.progressBar.isGone = true
                            if (actvCity.text.length > 1) {
                                bindNotification(true)
                                tvNotification.text = state.message
                            }

                        }
                    }
                }
            }
        }
    }

    private fun bindViewListeners() {
        with(binding) {

            actvCity.onItemClickListener = AdapterView.OnItemClickListener {
                    parent, _,
                    position, _,
                ->
                lifecycleScope.launch {
                    viewModel.saveRegion(parent.getItemAtPosition(position).toString())
                    findNavController().popBackStack()
                }
            }

            actvCity.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    lifecycleScope.launch {
                        viewModel.saveRegion(binding.actvCity.text.toString())
                        findNavController().popBackStack()
                    }
                    true
                } else false
            })
        }
    }

    private fun bindMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_bar_menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_accept -> {
                        lifecycleScope.launch {
                            viewModel.saveRegion(binding.actvCity.text.toString())
                            findNavController().popBackStack()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bindNotification(isError: Boolean) {
        val isGone = !isError

        binding.progressBar.isGone = true
        binding.ivNotification.isGone = isGone
        binding.tvNotification.isGone = isGone
    }
}