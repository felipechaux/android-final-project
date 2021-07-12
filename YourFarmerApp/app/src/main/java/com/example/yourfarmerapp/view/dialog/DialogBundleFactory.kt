package com.credibanco.smartpos.presentation.dialog

import android.os.Bundle
import com.example.yourfarmerapp.entities.Product

class DialogBundleFactory {

    companion object {
        const val PRODUCT =  "product"
        const val DIALOG_TYPE =  "dialogType"
        const val DISMISSABLE =  "dismissable"
        const val REGISTER_TYPE =  "registerDialogType"
        const val EDIT_PRODUCT_TYPE =  "editProductDialogType"
        const val LOADER_TYPE =  "loaderType"
    }


    fun getDialogBundle(
        dismissable: Boolean
    ): Bundle {
        val bundle = Bundle()
        bundle.putString(DIALOG_TYPE, REGISTER_TYPE)
        bundle.putBoolean(DISMISSABLE, dismissable)
        return bundle
    }

    fun getEditProductDialogBundle(
        product: Product,
        dismissable: Boolean
    ): Bundle {
        val bundle = Bundle()
        bundle.putString(DIALOG_TYPE, EDIT_PRODUCT_TYPE)
        bundle.putBoolean(DISMISSABLE, dismissable)
        bundle.putParcelable(PRODUCT,product)
        return bundle
    }
}