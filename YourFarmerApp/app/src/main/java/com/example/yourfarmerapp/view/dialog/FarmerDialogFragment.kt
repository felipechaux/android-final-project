package com.credibanco.smartpos.presentation.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.credibanco.smartpos.presentation.dialog.DialogBundleFactory.Companion.DISMISSABLE
import com.example.yourfarmerapp.R

class FarmerDialogFragment : DialogFragment() {
    private var positiveListener: (() -> Unit)? = null
    private var negativeListener: (() -> Unit)? = null
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = arguments?.get(DialogBundleFactory.DIALOG_TYPE)
        val dismissable = arguments?.getBoolean(DISMISSABLE, true)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        setListeners()

        dismissable?.let {
            dialog?.setCanceledOnTouchOutside(it)
        }

        return when(type) {
            DialogBundleFactory.REGISTER_TYPE -> setRegisterDialogValues(inflater, container)
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
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        sharedViewModel.removeListeners()
    }

    private fun setRegisterDialogValues(inflater: LayoutInflater, container: ViewGroup?): View{
        val view = inflater.inflate(R.layout.register_dialog, container, false)
        val positiveButton: TextView = view.findViewById(R.id.button_save)
        val btnClose: ImageView = view.findViewById(R.id.img_close)

        btnClose.setOnClickListener {
            dismiss()
        }
        positiveButton.setOnClickListener {
            onPositiveButtonClicked()
        }
        return view
    }


    private fun setLoaderDialogValues(inflater: LayoutInflater, container: ViewGroup?): View? {
       // val view = inflater.inflate(R.layout.loader_dialog, container, false)
      //  val title: TextView = view.findViewById(R.id.text_title)
      //  val progressIndicator: CircularProgressIndicator = view.findViewById(R.id.progress_indicator)

        arguments?.let {
      //      title.text = it.getString(DialogBundleFactory.DIALOG_TITLE)
        }

       // progressIndicator.show()

        return null
    }

    private fun onNegativeButtonClicked() {
        negativeListener?.invoke()
    }

    private fun onPositiveButtonClicked() {
        positiveListener?.invoke()
    }
}