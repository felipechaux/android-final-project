package com.credibanco.smartpos.presentation.dialog

import android.os.Bundle

class DialogBundleFactory {

    companion object {
        const val DIALOG_TITLE =  "title"
        const val IMAGE_RESOURCE =  "imgResource"
        const val FIRST_MESSAGE =  "firstMessage"
        const val SECOND_MESSAGE  =  "secondMessage"
        const val POSITIVE_BUTTON_TEXT =  "positiveButtonText"
        const val NEGATIVE_BUTTON_TEXT =  "negativeButtonText"
        const val DIALOG_TYPE =  "dialogType"
        const val DISMISSABLE =  "dismissable"

        const val REGISTER_TYPE =  "registerDialogType"
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
}