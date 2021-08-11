package com.felipechauxlab.yourfarmerapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _showTabLayout = MutableLiveData<Boolean>()
    private val _playWelcomeSound = MutableLiveData<Boolean>()
    private val _playMapSound = MutableLiveData<Boolean>()
    private val _requestLocation = MutableLiveData<Boolean>()

    val showTabLayout: LiveData<Boolean>
        get() = _showTabLayout

    val requestLocation: LiveData<Boolean>
        get() = _requestLocation

    val playWelcomeSound: LiveData<Boolean>
        get() = _playWelcomeSound

    val playMapSound: LiveData<Boolean>
        get() = _playMapSound


    fun showTabLayout() {
        _showTabLayout.value = true
    }

    fun hideTabLayout() {
        _showTabLayout.value = false
    }

    fun playWelcomeSound(){
        _playWelcomeSound.value=true
    }

    fun stopWelcomeSound(){
        _playWelcomeSound.value=false
    }

    fun playMapSound(){
        _playMapSound.value=true
    }

    fun stopMapSound(){
        _playMapSound.value=false
    }

    fun requestLocation(){
        _requestLocation.value=true
    }

}