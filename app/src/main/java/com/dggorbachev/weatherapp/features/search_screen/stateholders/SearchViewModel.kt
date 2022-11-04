package com.dggorbachev.weatherapp.features.search_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.data.remote_search_hints.RemoteSearchHintsRepo
import com.dggorbachev.weatherapp.domain.AsyncState
import com.dggorbachev.weatherapp.features.preferences_manager.PreferencesManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val searchHintsRepo: RemoteSearchHintsRepo,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val currentHints: SingleLiveEvent<AsyncState<ArrayList<String>>> =
        searchHintsRepo.hintState

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }

    fun getHints(term: String) {
        viewModelScope.launch {
            searchHintsRepo.get(term)
        }
    }

    suspend fun saveRegion(region: String) = withContext(Dispatchers.IO) {
        preferencesManager.updateRegion(region)
        preferencesManager.updateIsRegionPicked(true)
    }
}