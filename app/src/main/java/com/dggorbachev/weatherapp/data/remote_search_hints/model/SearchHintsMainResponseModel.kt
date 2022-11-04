package com.dggorbachev.weatherapp.data.remote_search_hints.model

import com.google.gson.annotations.SerializedName

data class SearchHintsMainResponseModel(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("country_name")
    val countryName: String,
)
