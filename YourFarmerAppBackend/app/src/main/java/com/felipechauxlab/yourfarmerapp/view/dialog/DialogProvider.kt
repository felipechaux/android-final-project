package com.felipechauxlab.yourfarmerapp.view.dialog

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel
import com.felipechauxlab.yourfarmerapp.entities.Product
import com.felipechauxlab.yourfarmerapp.entities.User

object DialogProvider {

    fun showRegisterDialog(
            fragment: Fragment,
            sharedDialogViewModel: SharedDialogViewModel,
            actionId: Int,
            dismissable: Boolean = true,
            positiveCallback: (User) -> Unit
    ) {
        val bundle = DialogBundleFactory().getDialogBundle(
            dismissable
        )
        fragment.findNavController().navigate(actionId, bundle)

        sharedDialogViewModel.setPositiveUserButtonListener {
            positiveCallback.invoke(it)
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