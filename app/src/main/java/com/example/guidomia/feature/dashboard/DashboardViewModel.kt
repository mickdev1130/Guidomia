package com.example.guidomia.feature.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guidomia.core.base.BaseViewModel
import com.example.guidomia.core.data.CarItem
import com.example.guidomia.core.extensions.getCarsList
import com.example.guidomia.core.preferences.DataStorePref
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorePref: DataStorePref
) : BaseViewModel() {

    private val _carsInit = MutableLiveData<List<CarItem>>()
    val carsInit: LiveData<List<CarItem>> = _carsInit

    private val _carResult = MutableLiveData<List<CarItem>>()
    val carResult: LiveData<List<CarItem>> = _carResult

    init {
        getCarList()
    }

    private fun getCarList() {
        viewModelScope.launch {
            setLoading(true)
            delay(1000)
            val cars = dataStorePref.carList.firstOrNull() ?: loadInitialCarList()
            val updatedCars = cars.map { it.copy(isExpanded = cars.indexOf(it) == 0) }
            _carsInit.value = updatedCars
            _carResult.value = updatedCars
            if (cars.isEmpty()) dataStorePref.saveCarList(updatedCars)
            setLoading(false)
        }
    }

    private fun loadInitialCarList(): List<CarItem> {
        val initialCars = context.getCarsList().toMutableList()
        initialCars.firstOrNull()?.isExpanded = true
        return initialCars
    }

    fun filterCars(makeName: String = "", modelName: String = "") {
        val filteredCars = _carsInit.value?.filter {
            it.make == makeName || it.model == modelName
        }?.mapIndexed { index, car ->
            car.copy(isExpanded = index == 0)
        }
        _carResult.value = filteredCars?.toMutableList()
    }

}