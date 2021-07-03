package com.example.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yourfarmerapp.view.dialog.DialogProvider
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

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
        return view
    }

    private fun addButtonsListeners() {
        binding?.buttonAccess?.setOnClickListener {
            goToIntroActivity()
        }
        binding?.buttonRegister?.setOnClickListener {
            showRegisterModal()
        }
    }

    private fun goToIntroActivity(){
        findNavController().navigate(R.id.action_loginFragment_to_introActivity)
    }

    private fun showRegisterModal(){
        DialogProvider.showRegisterDialog(
                this,
                R.id.action_loginFragment_to_farmerDialogFragment
        )
    }
}