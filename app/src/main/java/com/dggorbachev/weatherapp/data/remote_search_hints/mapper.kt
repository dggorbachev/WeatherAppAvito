package com.dggorbachev.weatherapp.data.remote_search_hints

import com.dggorbachev.weatherapp.data.remote_search_hints.model.SearchHintsMainResponseModel

object RemoteCurrentWeatherMapper {

    fun ArrayList<SearchHintsMainResponseModel>.toData(): ArrayList<String> {

        val res: ArrayList<String> = arrayListOf()

        for (hint in this) {
            res.add(hint.cityName + ", " + hint.countryName)
        }

        return res
    }
}