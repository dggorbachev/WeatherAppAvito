package com.dggorbachev.weatherapp.features.week_weather.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dggorbachev.weatherapp.databinding.ItemDayWeatherBinding
import com.dggorbachev.weatherapp.domain.model.Weather
import com.dggorbachev.weatherapp.domain.model.WeekWeather
import com.dggorbachev.weatherapp.features.week_weather.ui.view.WeekWeatherViewHolder

class WeekWeatherAdapter(
) :
    ListAdapter<WeekWeather, WeekWeatherViewHolder>(DiffCallback) {

    private var weekWeatherViewHolder: WeekWeatherViewHolder? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val binding = ItemDayWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        weekWeatherViewHolder = WeekWeatherViewHolder(binding)
        return weekWeatherViewHolder as WeekWeatherViewHolder
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<WeekWeather>() {
        override fun areItemsTheSame(oldItem: WeekWeather, newItem: WeekWeather): Boolean {
            return oldItem.latitude == newItem.latitude
        }

        override fun areContentsTheSame(oldItem: WeekWeather, newItem: WeekWeather): Boolean {
            return oldItem == newItem
        }
    }
}