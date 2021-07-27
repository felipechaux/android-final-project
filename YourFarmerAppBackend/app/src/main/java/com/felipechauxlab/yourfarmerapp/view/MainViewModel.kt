package com.felipechauxlab.yourfarmerapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _showTabLayout = MutableLiveData<Boolean>()

    val showTabLayout: LiveData<Boolean>
        get() = _showTabLayout

    fun showTabLayout() {
        _showTabLayout.value = true
    }

    fun hideTabLayout() {
        _showTabLayout.value = false
    }

}