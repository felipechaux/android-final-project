package com.felipechauxlab.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogBundleFactory
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.FragmentLoginBinding
import com.felipechauxlab.yourfarmerapp.view.MainViewModel
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogProvider
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel
import com.felipechauxlab.yourfarmerapp.entities.User
import com.felipechauxlab.yourfarmerapp.presenter.LoginFragmentPresenter
import com.felipechauxlab.yourfarmerapp.utils.NavigationUtils

class LoginFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: LoginFragmentPresenter by activityViewModels()
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding?.root
        navController = findNavController()
        addObservers()
        addButtonsListeners()
        mainViewModel.hideTabLayout()
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

        viewModel.goToIntro.observe(viewLifecycleOwner, Observer {
            if (it) {
                goToIntroActivity()
            }
        })

    }

    private fun showLoader() {
        val bundle = DialogBundleFactory().getDialogBundle(getString(R.string.text_loading), false)
        navController.navigate(R.id.action_loginFragment_to_farmerDialogFragment, bundle)
    }

    private fun addButtonsListeners() {
        binding?.buttonAccess?.setOnClickListener {
            val userLogin= User().apply {
                this.userName = binding?.textUser?.text.toString()
                this.password = binding?.textPassword?.text.toString()
            }
            activity?.let { context -> viewModel.authUser(context,userLogin) }
        }
        binding?.buttonRegister?.setOnClickListener {
            showRegisterDialog()
        }
    }

    private fun goToIntroActivity() {
        navController.navigate(R.id.action_loginFragment_to_introActivity)
    }

    private fun showRegisterDialog() {
        DialogProvider.showRegisterDialog(
                this,
                sharedViewModel,
                R.id.action_loginFragment_to_farmerDialogFragment, positiveCallback = { user ->
            NavigationUtils.closeDialog(navController, R.id.farmerDialogFragment)
            activity?.let { viewModel.registerUser(it, user) }
        })
    }
}