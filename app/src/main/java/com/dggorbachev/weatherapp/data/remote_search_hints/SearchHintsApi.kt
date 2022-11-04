package com.dggorbachev.weatherapp.data.remote_search_hints

import com.dggorbachev.weatherapp.data.remote_search_hints.model.SearchHintsMainResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SearchHintsApi {

    @GET
    suspend fun get(
        @Url url: String,
        @Query("term") term: String,
        @Query("locale") locale: String = "ru",
        @Query("types") types: List<String> = listOf("city"),
    ): ArrayList<SearchHintsMainResponseModel>
}