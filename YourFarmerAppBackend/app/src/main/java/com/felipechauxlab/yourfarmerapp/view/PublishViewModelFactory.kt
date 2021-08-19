package com.felipechauxlab.yourfarmerapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipechauxlab.yourfarmerapp.presenter.PublishFragmentPresenter

class PublishViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PublishFragmentPresenter() as T
    }
}
