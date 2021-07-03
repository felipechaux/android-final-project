package com.credibanco.smartpos.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedDialogViewModel : ViewModel() {
    private val _positiveListener = MutableLiveData<(() -> Unit)?>()
    private val _negativeListener = MutableLiveData<(() -> Unit)?>()

    val positiveListener: LiveData<(() -> Unit)?>
        get() = _positiveListener

    val negativeListener: LiveData<(() -> Unit)?>
        get() = _negativeListener

    fun setPositiveButtonListener(listener: () -> Unit) {
        _positiveListener.value = listener
    }

    fun setNegativeButtonListener(listener: () -> Unit) {
        _negativeListener.value = listener
    }

    fun removeListeners() {
        _negativeListener.value = null
        _positiveListener.value = null
    }
}