package com.dggorbachev.weatherapp.features.preferences_manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dggorbachev.weatherapp.base.dataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(private val context: Context) {

    val regionPreferencesFlow =
        context.dataStore.data.map { preferences ->
            val region = preferences[PreferencesKeys.REGION] ?: ""

            RegionPreferences(
                region
            )
        }

    val systemPreferencesFlow =
        context.dataStore.data.map { preferences ->
            val isRegionPicked = preferences[PreferencesKeys.IS_REGION_PICKED] ?: false

            SystemPreferences(
                isRegionPicked
            )
        }

    suspend fun updateRegion(region: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.REGION] = region
        }
    }

    suspend fun updateIsRegionPicked(isRegionPicked: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_REGION_PICKED] = isRegionPicked
        }
    }

    private object PreferencesKeys {
        val REGION = stringPreferencesKey("region")
        val IS_REGION_PICKED = booleanPreferencesKey("is_region_picked")
    }
}