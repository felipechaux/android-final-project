package com.example.yourfarmerapp.view.dialog

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.credibanco.smartpos.presentation.dialog.DialogBundleFactory

object DialogProvider {

    fun showRegisterDialog(
        fragment: Fragment,
        actionId: Int,
        dismissable: Boolean = true
    ) {
        val bundle = DialogBundleFactory().getDialogBundle(
            dismissable
        )
        fragment.findNavController().navigate(actionId, bundle)
    }

}