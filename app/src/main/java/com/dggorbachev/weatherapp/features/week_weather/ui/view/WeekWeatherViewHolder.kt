package com.dggorbachev.weatherapp.features.week_weather.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dggorbachev.weatherapp.R
import com.dggorbachev.weatherapp.base.exts.StringExt.firstCharUpper
import com.dggorbachev.weatherapp.base.exts.TextViewExt.setMessage
import com.dggorbachev.weatherapp.base.exts.TextViewExt.setMessage2Params
import com.dggorbachev.weatherapp.databinding.ItemDayWeatherBinding
import com.dggorbachev.weatherapp.domain.model.WeekWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.LONG

class WeekWeatherViewHolder(
    private val binding: ItemDayWeatherBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    private val calendar = Calendar.getInstance()

    fun bind(weather: WeekWeather) {
        bindDayOpenInfo(weather)
        bindDayHideInfo(weather)
        bindDropInfo()
    }

    private fun bindDayOpenInfo(weather: WeekWeather) {

        with(binding) {

            CoroutineScope(Dispatchers.IO).launch {
                calendar.timeInMillis = weather.date * 1000

                val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK,
                    LONG,
                    Locale("ru", "RU"))!!.firstCharUpper()

                val date =
                    calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + calendar.getDisplayName(
                        Calendar.MONTH,
                        LONG,
                        Locale("ru", "RU"))!!

                withContext(Dispatchers.Main) {
                    tvDate.setMessage2Params(R.string.date_message, dayOfWeek, date)
                }
            }
            tvWeatherDesc.text = weather.description.firstCharUpper()

            tvMinTemp.setMessage(R.string.temperature_message, weather.tempMin.toString())
            tvMaxTemp.setMessage(R.string.temperature_message, weather.tempMax.toString())
        }
    }

    private fun bindDayHideInfo(weather: WeekWeather) {
        binding.tvWind.setMessage(R.string.wind_speed_message, weather.windSpeed.toString())

        binding.tvHumidity.setMessage(R.string.humidity_message, weather.humidity.toString())

        CoroutineScope(Dispatchers.IO).launch {
            val dateFormat = SimpleDateFormat("HH:mm", Locale("ru", "RU"))

            calendar.timeInMillis = weather.sunrise * 1000
            val sunrise = dateFormat.format(calendar.time)

            calendar.timeInMillis = weather.sunset * 1000
            val sunset = dateFormat.format(calendar.time)

            withContext(Dispatchers.Main) {
                binding.tvSun.setMessage2Params(R.string.sunrise_sunset_message, sunrise, sunset)
            }
        }

        binding.tvFeelsLike.setMessage(R.string.temperature_message, weather.feelsLike.toString())
    }

    private fun bindDropInfo() {

        with(binding) {

            ivDrop.setOnClickListener {
                // Hidden information is close
                if (ivDrop.rotation == 0F) {
                    ivDrop.rotation = 180F
                    tlWeatherText.visibility = View.VISIBLE
                    tlWeatherInfo.visibility = View.VISIBLE
                }
                // Hidden information is open
                else if (ivDrop.rotation == 180F) {
                    ivDrop.rotation = 0F
                    tlWeatherText.visibility = View.GONE
                    tlWeatherInfo.visibility = View.GONE
                }
            }
        }
    }
}