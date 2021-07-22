package com.felipechauxlab.yourfarmerapp.view.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.FragmentPublishBinding
import com.felipechauxlab.yourfarmerapp.presenter.PublishFragmentPresenter
import com.felipechauxlab.yourfarmerapp.utils.NavigationUtils
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory


class PublishFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val viewModel: PublishFragmentPresenter by activityViewModels()
    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding
    private lateinit var navController: NavController
    private var quantitySelection: Int = 0
    private var productPhoto:String?=null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        val view = binding?.root
        navController = findNavController()
        addObservers()
        addButtonsListener()
        return view
    }

    private fun addObservers() {
        viewModel.showOrHideLoader.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoader()
            } else {
                NavigationUtils.closeDialog(navController, R.id.farmerDialogFragment)
            }
        })

        viewModel.uploadedPhotoSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                productPhoto=it
            }
        })

    }

    private fun showLoader() {
        val bundle = DialogBundleFactory().getDialogBundle(getString(R.string.text_loading), false)
        navController.navigate(R.id.action_homeFragment_to_farmerDialogFragment, bundle)
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

    private fun addButtonsListener() {
        val list = arrayOf(
                "1",
                "2",
                "3",
                "4"
        )
        binding?.quantityValue?.adapter =
                activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, list) }

        binding?.imgCamera?.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding?.buttonPublish?.setOnClickListener {
            activity?.let { context -> viewModel.validatePublishProduct(context,productPhoto,
                    binding?.textName?.text.toString(), binding?.textDescription?.text.toString(),
                    binding?.textValue?.text.toString(), quantitySelection) }
        }

        binding?.quantityValue?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                quantitySelection = parent?.getItemAtPosition(position).toString().toInt()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            binding?.imgCamera?.setImageBitmap(imgBitmap)
            val file = activity?.let { viewModel.bitmapToFile(it, imgBitmap) }
            file?.let { bitmapFile ->
                activity?.let { viewModel.uploadPhoto(it, bitmapFile) }
            }
        }
    }

}