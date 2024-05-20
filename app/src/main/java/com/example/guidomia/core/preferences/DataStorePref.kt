package com.example.guidomia.core.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.guidomia.core.data.CarItem
import com.example.guidomia.core.extensions.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "guidomia")

interface DataStorePref {


    val carList: Flow<List<CarItem>?>
    suspend fun saveCarList(carList: List<CarItem>)

    suspend fun clear()

    class DataStorePrefImpl @Inject constructor(private val context: Context) : DataStorePref {

        private val dataStore = context.dataStore
        private val gson = Gson()

        override val carList: Flow<List<CarItem>?>
            get() = dataStore.data.map { prefs ->
                gson.fromJson(prefs[PREF_KEY_CAR_LIST] ?: "")
            }
        override suspend fun saveCarList(carList: List<CarItem>) {
            carList?.let {
                dataStore.edit { prefs ->
                    prefs[PREF_KEY_CAR_LIST] = gson.toJson(carList)
                }
            }
        }

        override suspend fun clear() {
            dataStore.edit { prefs ->
                prefs.clear()
            }
        }

        companion object {
            private val PREF_KEY_CAR_LIST = stringPreferencesKey("pref_car_list")
        }
    }
}
