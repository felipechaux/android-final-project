package com.felipechauxlab.yourfarmerapp.view.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel
import com.felipechauxlab.yourfarmerapp.adapter.CircleTransform
import com.felipechauxlab.yourfarmerapp.entities.User
import com.felipechauxlab.yourfarmerapp.presenter.PublishFragmentPresenter
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.felipechauxlab.yourfarmerapp.utils.NavigationUtils
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory.Companion.DISMISSABLE
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory.Companion.PRODUCT
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso


class FarmerDialogFragment : DialogFragment() {
    private var positiveListener: (() -> Unit)? = null
    private var negativeListener: (() -> Unit)? = null
    private var userPositiveListener: ((User) -> Unit)? = null
    private var productPositiveListener: ((PublishProductDTO) -> Unit)? = null
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
    private var product: PublishProductDTO? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageProduct: ImageView
    private var productPhotoURL:String?=null
    private val viewModel: PublishFragmentPresenter by activityViewModels()

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
        addObservers()
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

    private fun addObservers() {
        viewModel.uploadedPhotoSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                productPhotoURL=it
                println("upload photo is $it")
            }
        })

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

        sharedViewModel.productPositiveListener.observe(viewLifecycleOwner, Observer {
            productPositiveListener = it
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
            Toast.makeText(activity, getString(R.string.complete_all_fields), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setEditProductDialogValues(inflater: LayoutInflater, container: ViewGroup?): View {
        resetPhoto()
        var quantitySelection: Int? = product?.productQuantity
        val view = inflater.inflate(R.layout.edit_product_dialog, container, false)
        imageProduct = view.findViewById(R.id.img_product)
        val positiveButton: ImageButton = view.findViewById(R.id.btn_edit)
        val quantityValueSpinner: Spinner = view.findViewById(R.id.quantity_value)
        val btnClose: ImageView = view.findViewById(R.id.img_close)
        val textProductName: TextView = view.findViewById(R.id.text_product)
        val textProductCost: TextView = view.findViewById(R.id.text_cost)
        val textProductDescription: TextView = view.findViewById(R.id.text_description)

        getImageUrl(imageProduct, product?.productPhoto.toString())
        textProductName.text = product?.productName
        textProductDescription.text = product?.productDescription
        textProductCost.text = product?.productCost
        val list = arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5"
        )
        quantityValueSpinner.adapter =
            activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, list) }

        quantityValueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                quantitySelection = parent?.getItemAtPosition(position).toString().toInt()
            }

        }
        btnClose.setOnClickListener {
            dismiss()
        }
        positiveButton.setOnClickListener {
            val product = PublishProductDTO().apply {
                this.productKey = product?.productKey
                this.productName = textProductName.text.toString()
                this.productDescription = textProductDescription.text.toString()
                this.productCost = textProductCost.text.toString()
                this.productPhoto = if(productPhotoURL!=null) productPhotoURL else product?.productPhoto
                this.productQuantity = quantitySelection
            }
            onPositiveProductButtonClicked(product)
            viewModel.resetValues()
        }

        imageProduct.setOnClickListener {
            dispatchTakePictureIntent()
        }

        return view
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    private fun setLoaderDialogValues(inflater: LayoutInflater, container: ViewGroup?): View? {
        val view = inflater.inflate(R.layout.loader_dialog, container, false)
        val title: TextView = view.findViewById(R.id.text_title)
        val progressIndicator: CircularProgressIndicator =
            view.findViewById(R.id.progress_indicator)
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

    private fun onPositiveProductButtonClicked(publishProductDTO: PublishProductDTO) {
        productPositiveListener?.invoke(publishProductDTO)
    }

    private fun getImageUrl(image: ImageView, url: String) {
        try {
            Picasso.get().load(url).transform(CircleTransform()).into(image)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun resetPhoto(){
        productPhotoURL=null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            imageProduct.setImageBitmap(imgBitmap)

            val file = activity?.let { viewModel.bitmapToFile(it, imgBitmap) }
            file?.let { bitmapFile ->
                activity?.let { viewModel.uploadPhoto(it, bitmapFile) }
                Picasso.get().load(bitmapFile).transform(CircleTransform()).into(imageProduct)
            }
        }
    }
}