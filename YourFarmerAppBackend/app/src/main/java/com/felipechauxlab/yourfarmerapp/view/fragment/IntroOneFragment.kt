package com.felipechauxlab.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yourfarmerapp.databinding.FragmentIntroOneBinding
import com.felipechauxlab.yourfarmerapp.view.MainViewModel

class IntroOneFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentIntroOneBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroOneBinding.inflate(inflater, container, false)
        val view = binding?.root
        addButtonsListener()
        mainViewModel.playWelcomeSound()
        return view
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.stopWelcomeSound()
    }

    private fun addButtonsListener() {
        binding?.btnWelcome?.setOnClickListener {
            mainViewModel.stopWelcomeSound()
        }
    }

}