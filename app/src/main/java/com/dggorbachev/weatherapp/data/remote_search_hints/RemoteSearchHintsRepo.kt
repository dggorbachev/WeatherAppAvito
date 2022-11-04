package com.dggorbachev.weatherapp.data.remote_search_hints

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.domain.AsyncState

interface RemoteSearchHintsRepo {
    val hintState: SingleLiveEvent<AsyncState<ArrayList<String>>>

    suspend fun get(term: String)
}