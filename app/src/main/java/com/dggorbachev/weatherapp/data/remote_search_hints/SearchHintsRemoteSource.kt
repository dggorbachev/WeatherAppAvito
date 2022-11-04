package com.dggorbachev.weatherapp.data.remote_search_hints

import com.dggorbachev.weatherapp.base.common.Constants.BASE_URL_AUTOCOMPLETE
import com.dggorbachev.weatherapp.data.remote_search_hints.model.SearchHintsMainResponseModel
import javax.inject.Inject

class SearchHintsRemoteSource @Inject constructor(private val searchHintsApi: SearchHintsApi) {

    suspend fun get(term: String): ArrayList<SearchHintsMainResponseModel> =
        searchHintsApi.get(url = BASE_URL_AUTOCOMPLETE, term = term)
}