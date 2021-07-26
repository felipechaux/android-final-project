package com.felipechauxlab.yourfarmerapp.presenter

import android.content.Context
import com.felipechauxlab.yourfarmerapp.view.fragment.IProductsFragmentView

interface IPublishFragmentPresenter {
    fun getPublishProducts(context: Context,iProductsFragmentView: IProductsFragmentView)

    fun showPublishProducts()

}