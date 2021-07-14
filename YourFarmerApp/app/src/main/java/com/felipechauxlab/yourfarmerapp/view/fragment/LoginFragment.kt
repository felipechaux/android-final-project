package com.example.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yourfarmerapp.view.dialog.DialogProvider
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.FragmentLoginBinding
import com.example.yourfarmerapp.view.MainViewModel
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel

class LoginFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding?.root

        addButtonsListeners()
        mainViewModel.hideTabLayout()
        return view
    }

    private fun addButtonsListeners() {
        binding?.buttonAccess?.setOnClickListener {
            goToIntroActivity()
        }
        binding?.buttonRegister?.setOnClickListener {
            showRegisterDialog()
        }
    }

    private fun goToIntroActivity() {
        findNavController().navigate(R.id.action_loginFragment_to_introActivity)
    }

    private fun showRegisterDialog() {
        DialogProvider.showRegisterDialog(
            this,
            sharedViewModel,
            R.id.action_loginFragment_to_farmerDialogFragment, positiveCallback = {
                println("register user")
            }
        )
    }
}