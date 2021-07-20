package com.felipechauxlab.yourfarmerapp.view.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory.Companion.DISMISSABLE
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory.Companion.PRODUCT
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.entities.Product
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel
import com.felipechauxlab.yourfarmerapp.entities.User
import com.felipechauxlab.yourfarmerapp.restApi.dto.request.RequestRegisterUserDTO
import com.google.android.material.progressindicator.CircularProgressIndicator


class FarmerDialogFragment : DialogFragment() {
    private var positiveListener: (() -> Unit)? = null
    private var negativeListener: (() -> Unit)? = null
    private var userPositiveListener: ((User) -> Unit)? = null
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
    private var product: Product? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val type = arguments?.get(DialogBundleFactory.DIALOG_TYPE)
        val dismissable = arguments?.getBoolean(DISMISSABLE, true)
        arguments?.run {
            product = getParcelable(PRODUCT)
        }
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        setListeners()

        dismissable?.let {
            dialog?.setCanceledOnTouchOutside(it)
        }

        return when (type) {
            DialogBundleFactory.REGISTER_TYPE -> setRegisterDialogValues(inflater, container)
            DialogBundleFactory.EDIT_PRODUCT_TYPE -> setEditProductDialogValues(inflater, container)
            DialogBundleFactory.LOADER_TYPE -> setLoaderDialogValues(inflater, container)
            else -> null
        }
    }

    private fun setListeners() {
        sharedViewModel.negativeListener.observe(viewLifecycleOwner, Observer {
            negativeListener = it
        })

        sharedViewModel.positiveListener.observe(viewLifecycleOwner, Observer {
            positiveListener = it
        })

        sharedViewModel.userPositiveListener.observe(viewLifecycleOwner, Observer {
            userPositiveListener = it
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        sharedViewModel.removeListeners()
    }

    private fun setRegisterDialogValues(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.register_dialog, container, false)
        val positiveButton: TextView = view.findViewById(R.id.button_save)
        val btnClose: ImageView = view.findViewById(R.id.img_close)

        btnClose.setOnClickListener {
            dismiss()
        }
        positiveButton.setOnClickListener {
            validateRegisterUserInputs(view)
        }
        return view
    }

    private fun validateRegisterUserInputs(view: View) {
        val textName: TextView = view.findViewById(R.id.text_name)
        val textID: TextView = view.findViewById(R.id.text_id)
        val textPassword: TextView = view.findViewById(R.id.text_password)
        val textPhoneNumber: TextView = view.findViewById(R.id.text_phone_number)
        val checkFarmer: CheckBox = view.findViewById(R.id.check_farmer)

        if (textName.text.isNotEmpty()
                && textID.text.isNotEmpty()
                && textPassword.text.isNotEmpty()
                && textPhoneNumber.text.isNotEmpty()
        ) {
            val user = User().apply {
                userName = textName.text.toString()
                identification = textID.text.toString()
                password = textPassword.text.toString()
                phoneNumber = textPhoneNumber.text.toString().toInt()
                isFarmer = checkFarmer.isChecked
            }
            onPositiveUserButtonClicked(user)
        } else {
            Toast.makeText(activity, getString(R.string.complete_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setEditProductDialogValues(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.edit_product_dialog, container, false)
        val positiveButton: ImageButton = view.findViewById(R.id.btn_edit)
        val quantityValueSpinner: Spinner = view.findViewById(R.id.quantity_value)
        val btnClose: ImageView = view.findViewById(R.id.img_close)
        val textProductName: TextView = view.findViewById(R.id.text_product)
        val textProductDescription: TextView = view.findViewById(R.id.text_description)

        textProductName.text = product?.productName
        textProductDescription.text = product?.productDescription
        val list = arrayOf(
                product?.productQuantity.toString(),
                "2",
                "3",
                "4"
        )
        quantityValueSpinner.adapter =
                activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, list) }

        btnClose.setOnClickListener {
            dismiss()
        }
        positiveButton.setOnClickListener {
            onPositiveButtonClicked()
        }

        return view
    }


    private fun setLoaderDialogValues(inflater: LayoutInflater, container: ViewGroup?): View? {
        val view = inflater.inflate(R.layout.loader_dialog, container, false)
        val title: TextView = view.findViewById(R.id.text_title)
        val progressIndicator: CircularProgressIndicator = view.findViewById(R.id.progress_indicator)
        arguments?.let {
            title.text = it.getString(DialogBundleFactory.DIALOG_TITLE)
        }
        progressIndicator.show()
        return view
    }

    private fun onPositiveButtonClicked() {
        positiveListener?.invoke()
    }

    private fun onPositiveUserButtonClicked(user: User) {
        userPositiveListener?.invoke(user)
    }
}