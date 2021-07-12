package com.example.yourfarmerapp.view.dialog

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.credibanco.smartpos.presentation.dialog.DialogBundleFactory
import com.example.yourfarmerapp.entities.Product

object DialogProvider {

    fun showRegisterDialog(
        fragment: Fragment,
        sharedDialogViewModel: SharedDialogViewModel,
        actionId: Int,
        dismissable: Boolean = true,
        positiveCallback: () -> Unit
    ) {
        val bundle = DialogBundleFactory().getDialogBundle(
            dismissable
        )
        fragment.findNavController().navigate(actionId, bundle)

        sharedDialogViewModel.setPositiveButtonListener {
            positiveCallback.invoke()
        }
    }

    fun showEditProductDialog(
        product: Product,
        fragment: Fragment,
        sharedDialogViewModel: SharedDialogViewModel,
        actionId: Int,
        dismissable: Boolean = true,
        positiveCallback: (Product) -> Unit

    ) {
        val bundle = DialogBundleFactory().getEditProductDialogBundle(
            product,
            dismissable
        )
        fragment.findNavController().navigate(actionId, bundle)

        sharedDialogViewModel.setPositiveButtonListener {
            positiveCallback.invoke(product)
        }
    }

}