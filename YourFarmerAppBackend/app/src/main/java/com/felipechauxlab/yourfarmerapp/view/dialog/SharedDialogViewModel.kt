package com.example.yourfarmerapp.view.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felipechauxlab.yourfarmerapp.entities.User

class SharedDialogViewModel : ViewModel() {
    private val _positiveListener = MutableLiveData<(() -> Unit)?>()
    private val _negativeListener = MutableLiveData<(() -> Unit)?>()
    private val _userPositiveListener = MutableLiveData<((User) -> Unit)?>()

    val positiveListener: LiveData<(() -> Unit)?>
        get() = _positiveListener

    val negativeListener: LiveData<(() -> Unit)?>
        get() = _negativeListener

    val userPositiveListener: LiveData<((User) -> Unit)?>
        get() = _userPositiveListener

    fun setPositiveButtonListener(listener: () -> Unit) {
        _positiveListener.value = listener
    }

    fun setPositiveUserButtonListener(listener: (User) -> Unit) {
        _userPositiveListener.value = listener
    }

    fun setNegativeButtonListener(listener: () -> Unit) {
        _negativeListener.value = listener
    }

    fun removeListeners() {
        _negativeListener.value = null
        _positiveListener.value = null
        _userPositiveListener.value = null
    }
}