package com.example.guidomia.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    fun setLoading(value: Boolean) {
        _loading.value = value
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun setError(value: String) {
        _error.value = value
    }

}