package com.dggorbachev.weatherapp.data.remote_search_hints

import com.dggorbachev.weatherapp.base.SingleLiveEvent
import com.dggorbachev.weatherapp.data.remote_search_hints.RemoteSearchHintsMapper.toData
import com.dggorbachev.weatherapp.domain.AsyncState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteSearchHintsRepoImpl @Inject constructor(
    private val source: SearchHintsRemoteSource,
) : RemoteSearchHintsRepo {

    override val hintState: SingleLiveEvent<AsyncState<ArrayList<String>>>
        get() = _hintState

    private val _hintState = SingleLiveEvent<AsyncState<ArrayList<String>>>()

    override suspend fun get(term: String) {
        withContext(Dispatchers.IO) {
            hintState.postValue(AsyncState.Loading)
            try {
                hintState.postValue(AsyncState.Loaded(source.get(term).toData()))
            } catch (e: java.net.SocketTimeoutException) {
                hintState.postValue(AsyncState.Error("Не удалось загрузить данные"))
            } catch (e: Exception) {
                hintState.postValue(AsyncState.Error("Что-то пошло не так..."))
            }
        }
    }
}